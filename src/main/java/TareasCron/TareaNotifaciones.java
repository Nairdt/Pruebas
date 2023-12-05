package TareasCron;

import Exportador.EnumerableTipoExportador;
import GeneradorRankings.RankingEntidadesConMayorCantIncidentes;
import GeneradorRankings.RankingImpactoProblematica;
import Notificador.NotificarSinApuros;
import org.quartz.SchedulerException;

public class TareaNotifaciones {
    public static void main(String[] args) throws SchedulerException {

        String destinatario = "";//uso repo de los miembros
        NotificarSinApuros notificador = new NotificarSinApuros();

        notificador.notificarPorLapso(destinatario);
    }

}
