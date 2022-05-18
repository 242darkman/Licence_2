
                        -- DM : Partie 1 / 2
data Formule = Var String
  | Non Formule
  | Et Formule Formule
  | Ou Formule Formule
  | Imp Formule Formule
  | Equi Formule Formule
      deriving (Eq, Show)


{-  2. Représentation en Haskell   -}
--Exemples de calcul propositionnel
f1 = (Ou (Et (Var "c") (Var "d")) (Et (Var "a") (Var "b")))
f2 = (Imp (Var "d") (Ou (Var "c") (Non (Var "b"))))
f3 = (Equi (Var "d") (Et (Var "c") (Var "d")))
f4 = (Imp (Non (Var "d")) (Ou (Var "c") (Var "b")))
f5 = (Non (Non (Var "a")))
f6 = (Imp (Non (Ou (Var "a") (Var "d"))) (Ou (Var "c") (Var "b")))


  -- Réponse 1 : Définition de la fonction (visuFormule f) permettant de visualiser une formule f
visuFormule :: Formule -> String
visuFormule (Var p) = p
visuFormule (Non f) = "~" ++ (visuFormule f)
visuFormule (Et g d) = "(" ++ (visuFormule g) ++ " & " ++ (visuFormule d) ++ ")"
visuFormule (Ou g d) = "(" ++ (visuFormule g) ++ " v " ++ (visuFormule d) ++ ")"
visuFormule (Imp g d) = "(" ++ (visuFormule g) ++ " => " ++ (visuFormule d) ++ ")"
visuFormule (Equi g d) = "(" ++ (visuFormule g) ++ " <=> " ++ (visuFormule d) ++ ")"
pprint = putStrLn . visuFormule



{-  3. Mise sous forme clausale d'une formule
      3.1 Etape #1 : éliminer les opérateurs Imp et Equi -}
    -- Réponse 2: Soient g et d deux formules.
      -- a) On peut remplacer (Imp g d) par (Ou (Non g) d) parce que par définition sous forme composé d'autre connecteurs
-- (Imp g d) équivaut à (Non (Et g (Non d))) ainsi qu'à (Ou (Non g) d)

      -- b) On peut remplacer (Equi g d) par (Et (Imp g d) (Imp g d)) et donc par (Et (Ou (Non g) d) (Ou (Non g) d)))

    -- Réponse 3: Définition de la fonction (elimine f) qui fait disparaitre les opérateurs Imp et Equi de la formule f.
elimine :: Formule -> Formule
elimine (Var p)    = Var p
elimine (Non f)    = Non (elimine f)
elimine (Et g d)   = Et (elimine g) (elimine d)
elimine (Ou g d)   = Ou (elimine g) (elimine d)
elimine (Imp g d)  = Ou (Non (elimine g)) (elimine d)
elimine (Equi g d) = Et (Ou (Non (elimine g)) (elimine d)) (Ou (elimine g) (Non d)) --Ou (Et g d) (Et (Non g) (Non d))


{-  3. Mise sous forme clausale d'une formule
      3.2 Etape #2 : amener les négations devant les littéraux positifs -}
f2b = (Imp (Non (Var "d")) (Ou (Var "c") (Var "b")))

    -- Réponse 4: Soit f une formule. On peut remplacer (Non (Non f)) par f
    -- Réponse 5: Rappelle des deux lois de De Morgan
        {- Première loi : (Imp (Non (Et g d)) (Ou (Non g) (Non d)))
           Deuxième loi : (Imp (Non (Ou g d)) (Et (Non g) (Non d)))
        -}
        -- On définit la fonction (ameneNon f) qui amène les négations devant les littéraux positifs de la formule f
ameneNon, disNon :: Formule -> Formule

ameneNon (Var p) = (Var p)
ameneNon (Non f) = disNon f
ameneNon (Et g d) = (Et (ameneNon g) (ameneNon d))
ameneNon (Ou g d) = (Ou (ameneNon g) (ameneNon d))

    -- Réponse 6: La fonction (disNon f) enlève les négations sur les litteraux négatifs
disNon (Var p)  = (Non (Var p))
disNon (Non f)  = ameneNon f
disNon (Et g d) = (Ou (disNon g) (disNon d))
disNon (Ou g d) = (Et (disNon g) (disNon d))



{-  3. Mise sous forme clausale d'une formule
      3.3 Etape #3 : faire apparaitre une conjonction de clauses -}
normalise :: Formule -> Formule
normalise (Et g d) = concEt (normalise g) (normalise d)
normalise (Ou g d) = developper (normalise g) (normalise d)
normalise f        = f

concEt :: Formule -> Formule -> Formule
concEt (Et g d) f = (Et g (concEt d f))
concEt g f        = (Et g f)

--( ((a ou c) et (a ou d)) et ((b ou c) et (b ou d)))
    -- Réponse 7: Définition de la fonction (developper g d) (qui pourra utiliser une ou plusieurs fonctions auxiliaires)
developperAux :: Formule -> Formule -> Formule
developperAux f (Et g d) = (Et (developperAux g f ) (developperAux d f))
developperAux f g = (Ou g f)

developper :: Formule -> Formule -> Formule
developper (Et g d) f = concEt (developperAux g f) (developperAux d f)
developper f (Et g d) = concEt (developperAux g f) (developperAux d f)
developper f g        = (Ou f g)


        -- Réponse 8: En déduire la définition de la fonction (formeClausale f) qui applique successivement chacune des trois étapes à la formule f
formeClausale :: Formule -> Formule
formeClausale f = normalise (ameneNon (elimine f))




                                      -- DM : Partie 2 / 2
                              {- Résolvante et Principe de résolution -}
type Clause = [Formule]
type FormuleBis = [Clause]

      {- 1. Transformer une Formule en une FormuleBis-}
        -- Réponse 9: Complétons les définitions des fonctions ci-dessous qui permettent de transformer une formule f
        -- (déjà sous forme clausale) en une FormuleBis, i.e. en une liste de clauses.
etToListe :: Formule -> FormuleBis
etToListe (Et g d) = (ouToListe g) : etToListe d
etToListe f        = [ouToListe f]

ouToListe :: Formule -> Clause
ouToListe (Ou g d) = g : (ouToListe d)
ouToListe f        = [f]


-- fonction introduite pour une meilleure lisibilité
--
formeClausaleBis :: Formule -> FormuleBis
formeClausaleBis = etToListe



        -- Réponse 10: Définition de la fonction (neg l) qui à un littéral l associe sa négation.
neg :: Formule -> Formule
neg (Non l) = l
neg l       = (Non l)


        -- Réponse 11: Complétons la définition de la fonction (sontLiees xs ys) qui détermine si deux clauses xs et ys sont liées.
sontLiees :: Clause -> Clause -> Bool
sontLiees [] _ = False
sontLiees _ [] = False
sontLiees (x:xs) (y:ys)
  | x == (neg y) = True
  | sontLiees xs (y:ys) == False = sontLiees (x:xs) ys
  | sontLiees (x:xs) ys == False = sontLiees xs (y:ys)
  | otherwise = True



        -- Réponse 12: Complétons la définition de la fonction (resolvante xs ys) qui calcule une résolvante des deux
        -- clauses xs et ys qui sont supposées être liées
estDansClause :: Formule -> Clause -> Bool
estDansClause _ [] = False
estDansClause x (y:ys)
  | x == y = True
  | otherwise = estDansClause x ys

resolvante :: Clause -> Clause -> Clause
resolvante [] _ = []
resolvante _ [] = []
resolvante (x:xs) (y:ys)
  | x == (neg y) = xs ++ ys
  | x == y = x:(resolvante xs ys)
  | otherwise = if (estDansClause x(resolvante xs (y:ys)))
    then (resolvante xs (y:ys))
    else (x:(resolvante xs (y:ys)))




  -- FIN PARTIE 2
  --



deduire :: Formule -> Clause
deduire x = resoudre (head sorite) (tail sorite)
    where sorite = (formeClausaleBis (formeClausale x))

resoudre :: Clause -> FormuleBis -> Clause
resoudre xs [] = xs
resoudre xs (ys:yss)
  | sontLiees xs ys  = resoudre (resolvante xs ys) yss
  | otherwise  = resoudre xs (yss ++ [ys])




exemple1 = (Et (Imp (Var "A") (Var "B"))
              (Et (Imp (Var "B") (Var "C"))
                (Et (Imp (Var "C") (Non (Var "D")))
                  (Et (Imp (Non (Var "D")) (Non (Var "E")))
                      (Imp (Non (Var "E")) (Var "F"))))))



exemple2 = (Et (Imp (Non (Var "A")) (Var "B"))
              (Et (Imp (Var "C") (Non (Var "D")))
                (Et (Imp (Var "E") (Non (Var "F")))
                  (Et (Imp (Non (Var "D")) (Non (Var "B")))
                      (Imp (Var "A") (Var "F"))))))


  -- autre formulation de exemple #2
a = "etre un exercice qui me fait ronchonner"
b = "etre un exercice que je comprends"
c = "etre parmi ces sorites"
d = "etre dispose regulierement, comme les exercices auxquels je suis habitue"
e = "etre un exercice facile"
f = "etre un exercice qui me donne  mal a la tete"


testBis = (Et (Imp (Non (Var a)) (Var b))
            (Et (Imp (Var c) (Non (Var d)))
                (Et (Imp (Var e) (Non (Var f)))
                    (Et (Imp (Non (Var d)) (Non (Var b)))
                        (Imp (Var a) (Var f))))))


  -- exemple 3 - les bebes
bebe = Et (Imp (Var "bebe") (Non (Var "logique")))
          (Et (Imp (Var "tuer crocodile") (Non (Var "meprise")))
              (Imp (Non (Var "logique")) (Var "meprise")))
