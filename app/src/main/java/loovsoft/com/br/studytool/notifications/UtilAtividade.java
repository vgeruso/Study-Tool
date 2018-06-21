package loovsoft.com.br.studytool.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class UtilAtividade {
    private static PendingIntent alarmIntent;

    public static void scheduleNotification(Context context, String date) {
        //A partir de uma String recebida com o formato hh:mm é o horário da notificação é criado
        String partes[] = date.split("/");
        int day = Integer.parseInt(partes[0]);
        int mounth = Integer.parseInt(partes[1]);

        //Em seguida criamos uma instância da classe Alarm Mangager e uma Intent indicando quem
        //deve ser chamado quando o momento chegar, nesse casso a classe MyNotificationSystem
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationAtividade.class);


        //Verificamos se o evento já está agendado
        boolean isWorking = (PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_NO_CREATE) != null);

        //Se o evento não está agendado precisamos agendar
        if (!isWorking) {
            //Criamos uma Pending Intenten
            alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            //Definimos o agendamento de acordo com o que recebemos
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, mounth);

            //Verificamos se o evento foi agendado para uma data no futuro e, se não,
            //colocamos mais um dia no agendamento
            //Nunca se deve agendar um evento num tempo passado! (coisas estranhas acontecem)
            if(!calendar.after(Calendar.getInstance()))
                calendar.roll(Calendar.DATE, true);

            //Criamos nosso agendamento
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
    }
}