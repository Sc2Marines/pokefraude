package src.models.bots.instances.genetiq;
import src.models.Terrain;
import src.models.bots.models.GameReportModel;

public class WheightsLinke {
    private double[] weights;
    private GameReportModel gameReport;
    public WheightsLinke(double[] weights,GameReportModel  gameReport) {
        this.weights = weights;
        this.gameReport = gameReport;
    }


    public double getTerrainInfluence() {
        // Normaliser l'état du terrain
        double terrainState = gameReport.getActualTerrain().getEtat() == Terrain.TypeTerrain.NORMAL ? 0 : 1;
        terrainState /= 2.0;    // Normalisation

        // Normaliser le nombre de tours avant que le terrain redevienne normal
        double turnsBeforeNormal = gameReport.getActualTerrain().getTurnLeftBeforeNormal();
        turnsBeforeNormal /= 3.0;

        // Appliquer les poids
        double terrainInfluence = terrainState * weights[0] + turnsBeforeNormal * weights[1];
        return terrainInfluence;
    }


}
