package util;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModeleEcoutable implements ModeleEcoutable {

    private List<EcouteurModel> ecouteurs;

    /**
     * Nous amenons l'arrayList créer dans l'autre consctructeur
     * @param ecouteurs
     */
    public AbstractModeleEcoutable (List<EcouteurModel> ecouteurs) {
        this.ecouteurs = ecouteurs;
    }

    /**
     * Constructeur initialisant l'arrayList
     */
    public AbstractModeleEcoutable () {
        this(new ArrayList<>());
    }

    @Override
    public void ajoutEcouteur(EcouteurModel e) {
        this.ecouteurs.add(e);
        e.modelMiseAJour(this);
    }

    @Override
    public void retraitEcouteur(EcouteurModel e) {
        this.ecouteurs.remove(e);
    }

    /**
     * On notifie les ecouteurs du changements
     */
    public void notifierLesEcouteurs() {
        for (EcouteurModel e : ecouteurs) {
            e.modelMiseAJour(this);
        }
    }

}
