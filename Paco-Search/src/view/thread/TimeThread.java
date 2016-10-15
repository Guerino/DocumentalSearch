package view.thread;

import javax.swing.JLabel;

/**
 *
 * @author
 * Guerino
 */
public class TimeThread extends Thread{
    private int hora;
    private int minutos;
    private int segundos;
    private boolean stopTiempo;
    private boolean pausarTiempo;
    private JLabel lblTiempoTranscurrido;

    public TimeThread(JLabel tiempo) {
        this.segundos = 0;
        this.minutos = 0;        
        this.hora = 0;
        this.stopTiempo = false;
        this.pausarTiempo = false;
        this.lblTiempoTranscurrido = tiempo;
    }

    @Override
    public void run() {       
        // Formato de tiempo transcurrido
        String display = String.format("%02d:%02d:%02d", hora, minutos, segundos);
        this.lblTiempoTranscurrido.setText(display);  

        try {            
             while (!stopTiempo)
             { 
//                if(pausarTiempo){
//                    Thread.sleep(100000000);
//                }
                Thread.sleep(1000);
                segundos += 1;
                if(segundos == 60){ 
                    segundos = 0;
                    minutos +=1;
                }else                
                    if(minutos == 60){
                        minutos = 0;
                        hora += 1;
                    }
                //Formato de tiempo transcurrido
                display = String.format("%02d:%02d:%02d", hora, minutos, segundos);
                 this.lblTiempoTranscurrido.setText(display); 
                
//                //TODO ver como calcular bien el tiempo restante
//                controlador.setTiempoRestanteEstimado();

             }
        } catch (Exception e) {
             System.out.println("Error: " + e.getMessage());
        }
        
        // Reiniciar contadores
        this.segundos = 0;
        this.minutos = 0;        
        this.hora = 0;
        this.stopTiempo = false;
    } 
    
    public void stopTiempo(boolean stop){
        this.stopTiempo = stop;
    }
    
    public void pausarTiempo(boolean pausa){
        this.pausarTiempo = pausa;
    }
}