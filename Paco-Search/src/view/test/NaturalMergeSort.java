package view.test;

import PacoSearch.util.Tuple;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Estructura interna del archivo a ordenar: 
   word | tf | docId  | ...
 * @author
 * Guerino
 */
public class NaturalMergeSort {    
    
    public void ordenar(String path, String nombreArchivo){
        int index = 0;
        while(particion(path + nombreArchivo, path + "\\temp1.dat", path + "\\temp2.dat")){
            //Imprime el numero de particiones-fusiones que le llevo a los
            //metodos de particion y fusion el ordenar el archivo
            System.out.println("Fusion " + ++index);
            fusion(path + nombreArchivo, path + "\\temp1.dat", path + "\\temp2.dat");
        }
        
        File t1 = new File(path + "\\temp1.dat");
        if(t1.exists())
            t1.delete();
        
        File t2 = new File(path + "\\temp2.dat");
        if(t2.exists())
            t2.delete();
    }
    
    //Metodo para generar particiones de secuencias
    private boolean particion(String nombreArchivo, String archivo1, String archivo2){
        //Estructura del archivo: 
        // word | tf | docId  | ...
        //Se utilizara una logica similar a la del metodo de verificar orden
        //por lo que los indices son declarados de la misma manera

        //agregados por mi: Guerino
        Tuple tActual = null;
        Tuple tAnterior = null;

        //Variable para controlar el indice del archivo al cual se va a escribir.
        //El archivo en cuestion es declarado dentro de un arreglo de archivos
        int indexOutputStream = 0;

        //Variable que determina si existe un cambio de secuencia en el ordenamiento
        boolean hayCambioDeSecuencia = false;

        //Declaracion de los objetos asociados a los archivos y del arreglo de archivos
        //que sirven para las particiones
        DataOutputStream dos[] = new DataOutputStream[2];
        DataInputStream dis = null;

	try{
            //Abre o crea los archivos
            dos[0] = new DataOutputStream(new FileOutputStream(archivo1,false));
            dos[1] = new DataOutputStream(new FileOutputStream(archivo2, false));
            dis = new DataInputStream( new FileInputStream(nombreArchivo));

            //Primero, verifica si existen datos en el archivo que se va a leer
            while(dis.available() != 0){
                //Utiliza la misma logica para las variables que almacenan los datos
                //que en el metodo de la verificacion del orden
                tAnterior = tActual;
                
                String word = dis.readUTF();
                int docId = dis.readInt();
                int tf = dis.readInt();
                
                
                tActual = new Tuple(word, tf, docId);
                if(tAnterior == null){
                    tAnterior = tActual;
                }
//               System.out.println("indexOutputStream-before: " + indexOutputStream);
                //Cambio de secuencia. Manipulacion del indice del arreglo de archivos               
                if(tAnterior.getWord().compareTo(tActual.getWord()) > 0){
                    indexOutputStream = indexOutputStream == 0 ? 1 : 0;
                    //Actualizacion de la variable booleana, esto indica la existencia
                    //de un cambio de secuencia
                    hayCambioDeSecuencia = true;
                }

                //Imprimir el dato contenido en actual y escribirlo en el archivo correspondiente
                dos[indexOutputStream].writeUTF(tActual.getWord().trim());
                dos[indexOutputStream].writeInt(tActual.getTf());
                dos[indexOutputStream].writeInt(tActual.getDocId());
                
               // System.out.println("indexOutputStream-after: " + indexOutputStream);
            }
	}
	catch (FileNotFoundException e){
            System.out.println("Error lectura/escritura");
	}
        catch (IOException e){
            System.out.println("Error en la creacion o apertura del archivo 1");
	}
        finally{
            //Verificar para cada archivo, que efectivamente se encuentre abierto
            //antes de cerrarlo
            try {
                if(dis != null){
                    dis.close();
                }

                if(dos[0] != null){
                    dos[0].close();
                }

                if(dos[1] != null){
                    dos[1].close();
                }
            } catch (IOException ex) {
                System.out.println("Error al cerrar archivos");
            }
        }
        //El valor retornado sirve para determinar cuando existe una particion
        return hayCambioDeSecuencia;
    }

    //Metodo de fusion de los datos obtenidos en el metodo de particion
    private void fusion(String nombreArchivo, String archivo1, String archivo2){
        //Variables para almacenar los datos de los archivos
        //que contienen las particiones
        Tuple[] actual = new Tuple[2];
        Tuple[] anterior = new Tuple[2];
        boolean[] finArchivo = new boolean[2];
        int indexArchivo = 0;

        //Creacion de los objetos asociacos a los archivos
        DataOutputStream dos = null;
        DataInputStream dis [] = new DataInputStream[2];

        try{
            //Abre o crea los archivos
            dis[0] = new DataInputStream(new FileInputStream(archivo1));
            dis[1] = new DataInputStream(new FileInputStream(archivo2));
            dos = new DataOutputStream( new FileOutputStream(nombreArchivo, false));

            //Condicion principal: debe haber datos en ambos archivos de lectura
            //Es importante notar que al inicio siempre hay al menos un dato en
            //cada archivo, de otra forma el metodo de particion hubiera
            //generado una sola secuencia y no entrariamos a la fusion.
            while(dis[0].available() != 0 && dis[1].available() != 0){
                // 1era vez: inicializar con la primera palabra de cada archivo              
                if(anterior[0] == null && anterior[1] == null){
                    String strWord0 = dis[0].readUTF();
                    int docId0 = dis[0].readInt();  
                    int tf0 = dis[0].readInt();
                                      
                    //System.out.println("[0]: " + strWord0 + ", " + tf0 + ", " + docId0);
                   
                    actual[0] = new Tuple();
                    anterior[0] = new Tuple();
                    
                    actual[0].setWord(strWord0);
                    actual[0].setTf(tf0);
                    actual[0].setDocId(docId0);
                        anterior[0].setWord(strWord0);
                        anterior[0].setTf(tf0);
                        anterior[0].setDocId(docId0);
                    ////////////////////////////////////////
                    String strWord1 = dis[1].readUTF();
                    int docId1 = dis[1].readInt();
                    int tf1 = dis[1].readInt();
                   
                    
                    actual[1] = new Tuple();
                    anterior[1] = new Tuple();
                   
                    //System.out.println("[1]: " + strWord1 + ", " + tf1 + ", " + docId1);
                    actual[1].setWord(strWord1);
                    actual[1].setTf(tf1);
                    actual[1].setDocId(docId1);
                        anterior[1].setWord(strWord1);
                        anterior[1].setTf(tf1);
                        anterior[1].setDocId(docId1);                  
                }
                
                // al inicio del procesamiento de dos secuencias, anterior y
                // actual apuntan a la primer palabra de cada secuencia.
                anterior[0] = actual[0];
                anterior[1] = actual[1];
                // mezclamos las dos secuencias hasta que una acaba
                while(anterior[0].getWord().compareTo(actual[0].getWord()) <= 0 &&
                      anterior[1].getWord().compareTo(actual[1].getWord()) <= 0 ){
                    indexArchivo = (actual[0].getWord().compareTo(actual[1].getWord()) <= 0) ? 0 : 1 ;
                    
                    dos.writeUTF(actual[indexArchivo].getWord());
                    dos.writeInt(actual[indexArchivo].getTf());
                    dos.writeInt(actual[indexArchivo].getDocId());
                    
                    anterior[indexArchivo] = actual[indexArchivo];

                    // salir del while cuando no haya datos, pero ya procesamos
                    // el ultimo nombre del archivo
                    if(dis[indexArchivo].available() != 0){
                          actual[indexArchivo].setWord(dis[indexArchivo].readUTF());
                          actual[indexArchivo].setTf(dis[indexArchivo].readInt());
                          actual[indexArchivo].setDocId(dis[indexArchivo].readInt());
                    } else {
                        finArchivo[indexArchivo] = true;
                        break;
                    }
                }

                // en este punto indexArchivo nos dice que archivo causo
                // que salieramos del while anterior, por lo que tenemos
                // que purgar el otro archivo
                indexArchivo = indexArchivo == 0 ? 1 : 0 ;

                while(anterior[indexArchivo].getWord().compareTo(actual[indexArchivo].getWord()) <= 0){
                    dos.writeUTF(actual[indexArchivo].getWord());
                    dos.writeInt(actual[indexArchivo].getTf());
                    dos.writeInt(actual[indexArchivo].getDocId());
                    
                    anterior[indexArchivo] = actual[indexArchivo];
                    if(dis[indexArchivo].available() != 0){
                         actual[indexArchivo].setWord(dis[indexArchivo].readUTF());
                         actual[indexArchivo].setTf(dis[indexArchivo].readInt());
                         actual[indexArchivo].setDocId(dis[indexArchivo].readInt());
                    } else {
                        finArchivo[indexArchivo] = true;
                        break;
                    }
                }
            }

            // purgar los dos archivos en caso de que alguna secuencia
            // haya quedado sola al final del archivo.
            // Para salir del while anterior alguno de los 2 archivos
            // debio terminar, por lo que a lo mas uno de los dos whiles
            // siguientes se ejecutara
            if(!finArchivo[0]){
                 dos.writeUTF(actual[0].getWord());
                 dos.writeInt(actual[0].getTf());
                 dos.writeInt(actual[0].getDocId());
                
                while(dis[0].available() != 0){
                    dos.writeUTF(dis[0].readUTF());
                    dos.writeInt(dis[0].readInt());
                    dos.writeInt(dis[0].readInt());
                }
            }

            if(!finArchivo[1]){
                 dos.writeUTF(actual[1].getWord());
                 dos.writeInt(actual[1].getTf());
                 dos.writeInt(actual[1].getDocId());
                 
                while(dis[1].available() != 0){
                     dos.writeUTF(dis[1].readUTF());
                     dos.writeInt(dis[1].readInt());
                     dos.writeInt(dis[1].readInt());
                }
            }
        }
        catch(FileNotFoundException e){
            System.err.println(e);
        }
        catch(IOException e){
            System.err.println(e);
        }
         finally{
            //Verificar para cada archivo, que efectivamente se encuentre abierto
            //antes de cerrarlo
            try {
                if(dis[0] != null){
                    dis[0].close();
                }

                if(dis[1] != null){
                    dis[1].close();
                }

                if(dos != null){
                    dos.close();
                }
            } catch (IOException ex) {
                System.out.println("Error al cerrar archivos");
            }
        }
    }
    
      //Metodo para verificar el correcto orden en el archivo
    public void verificarOrdenamiento(String nombreArchivo)throws IOException{
        String actual = null;
        String anterior = null;

        //Variable booleana para indicar el estado del archivo
        boolean estaOrdenado = true;

        DataInputStream dis = null;
        //DataOutputStream dos = null;
	try{
            dis = new DataInputStream( new FileInputStream(nombreArchivo));

            //Ciclo para verificar el orden del archivo
            //Comenzar siempre por averiguar si hay datos dentro del archivo
            while(dis.available() != 0){
                //En un primer momento los indices quedan a la par
                anterior = actual;
                //actual se encargara de ir "jalando" a anterior
                actual = dis.readUTF();

                //En la segunda vuelta, el indice anterior ocupa la posicion
                //del indice actual y a partir de aqui, el indice actual
                //se despega del anterior
                if(anterior == null){
                    anterior = actual;
                }

                System.out.println(actual);

                //Comparacion de los datos contenidos en actual y anterior
                //Condicion: Si el dato anterior es lexicograficamente mayor al actual
                if(anterior.compareTo(actual) > 0){
                    System.out.println("Error en el ordenamiento");
                    //Actualizacion de la variable booleana que indica el estado del archivo
                    estaOrdenado = false;
                    //Interrupcion del ciclo
                    break;
                }
            }

            //Si la variable booleana conservo su valor original de true, desplegar un mensaje
            if(estaOrdenado){
                System.out.println("EL ARCHIVO ESTA ORDENADO");
            }

	}
	catch (FileNotFoundException e){
            System.out.println("Error de Apertura-Lectura archivo: "+nombreArchivo);
        }
        catch (IOException e){
            System.out.println("Error de lectura archivo: "+nombreArchivo);
	}
        finally{
            //Verificar siempre que el archivo este abierto antes de intentar cerrarlo
            if(dis != null){
                dis.close();
            }

        }
    }    
  
}