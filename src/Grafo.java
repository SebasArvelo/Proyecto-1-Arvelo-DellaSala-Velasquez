import java.io.FileWriter;
import java.io.IOException;

/**
* Representa un grafo implementado con una lista de adyacencia
*/
class Grafo {
    ListaAdyacencia listaAd;
   
    /**
    * Agregar un usuario al grafo, si el id es único
    */
    public boolean agregarNodo(Usuario usuario){
        int indice = listaAd.obtenerIndiceUsuario(usuario.id);
        
        if(indice == -1){
            listaAd.agregarNodo(usuario);
            return true;
        } else {
            return false;
        }
    }
    
    /**
    * Agregar un arco al grafo, si no se encuentra ya agregado
    */
    public boolean agregarArco(int id, int idAmigo, int amistad){
        int indice = listaAd.obtenerIndiceUsuario(id);
        FilaAdyacencia fila = listaAd.obtenerFila(indice);
        
        Arco amigo = fila.amigos.obtener(idAmigo);
        if(amigo == null){
            listaAd.agregarArco(id, idAmigo, amistad);
            listaAd.agregarArco(idAmigo,id, amistad);
            return true;
        } else {
            return false;
        }
    }
    
    /**
    * Eliminar un usuario y sus amistades de la lista de adyacencia
    */
    public void eliminarNodo(int id){ 
        int n = listaAd.n;
        for(int i=0; i < n; i++){
            FilaAdyacencia fila = listaAd.obtenerFila(i);
            int m = fila.amigos.n;
            for(int j = 0; j < m; j++){       
                Arco arco = fila.amigos.obtenerPorIndice(j);
                if(arco.id == id){
                    fila.eliminarAmigo(id);
                    m--;
                }
            }
        }
        listaAd.eliminarNodo(id);
    }
    
    /**
    * Recorrer todos los nodos alcanzables desde el nodo i, con dfs
    */
    public String recorridoDFS (int i, boolean[] recorridos){
        recorridos[i] = true; 
        FilaAdyacencia fila = listaAd.obtenerFila(i);
        String isla = fila.usuario.id + " ";
            
        int n = fila.amigos.n;
        for(int j = 0; j < n; j++){
            Arco arco = fila.amigos.obtenerPorIndice(j);
            int indexAmigo = listaAd.obtenerIndiceUsuario(arco.id);
            
            if (arco.amistad > -1){
                if(!recorridos[indexAmigo]){
                    isla += recorridoDFS(indexAmigo, recorridos);
                }
            }
        }
        
        return isla;
    }
    
    /**
    * Recorrer todos los nodos alcanzables desde el nodo i, con bfs
    */
    public String recorridoBFS(int i, boolean[] recorridos){
        recorridos[i] = true;
        Amigos usuarios = new Amigos();
        String isla = "";
        
        int idUsuario = listaAd.obtenerFila(i).usuario.id;
        usuarios.agregar(-1, idUsuario);
        
        while (usuarios.n != 0){
            Arco usuario = usuarios.obtenerPorIndice(0);
            isla +=  Integer.toString(usuario.id) + " ";
            usuarios.eliminar(usuario.id);
            
            int indice  = listaAd.obtenerIndiceUsuario(usuario.id);
            FilaAdyacencia fila = listaAd.obtenerFila(indice);
            int n = fila.amigos.n;
            for(int j = 0; j < n; j++){
                Arco arco = fila.amigos.obtenerPorIndice(j);
                int indiceUsuario = listaAd.obtenerIndiceUsuario(arco.id);
                if (arco.amistad > -1){
                    if(!recorridos[indiceUsuario]){
                        usuarios.agregar(-1, arco.id);
                        recorridos[indiceUsuario] = true;
                    }
                }
            }
        }   
        return isla;
    }
    
    /**
    * Obtiener una cadena con todas las islas
    */
    public String obtenerIslas(String metodo){
        String islas = "";
        int n = listaAd.n;
        boolean[] recorridos = new boolean[n];
        for(int i=0; i < n; i++){
            recorridos[i] = false;
        }
            
        if(metodo.equals("dfs")){
            for (int i = 0; i < n; ++i) {
                if (!recorridos[i]){
                    islas += recorridoDFS(i, recorridos) + "\n\n";
                }
            }
        } else if (metodo.equals("bfs")){
            for (int i = 0; i < n; ++i) {
                if (!recorridos[i]){
                    islas+= recorridoBFS(i, recorridos) + "\n\n";
                }
            }
        }
        
        return islas;
    }
    
    /**
    * Contar el número de islas dada una cadena de islas
    */
    public int contarIslas(String islas){
        String separador = "\n\n";
        int indiceAnterior = 0;
        int n = 0;

        while (indiceAnterior != -1) {
            indiceAnterior = islas.indexOf(separador, indiceAnterior);
            if (indiceAnterior != -1) {
                n++;
                indiceAnterior += separador.length();
            }
        }
        return n;
    }
    
    /**
    * Obtiene una cadena con todos los puentes identificados, para quitar un arco,
    * se marca la amistad con un valor negativo, y luego para agregarlo nuevamente se restaura el valor de la amistad
    */
    public String obtenerPuentes(){
        int n = listaAd.n;
        String puentes = "";
        
        String islas1 = obtenerIslas("bfs");
        int nIslas1 = contarIslas(islas1);
                
        for(int i=0; i <  n; i++){
            FilaAdyacencia fila = listaAd.obtenerFila(i);
            int idUsuario =  fila.usuario.id;
            int nAmistades =  fila.amigos.n;
            for(int j=0; j < nAmistades; j++){
                Arco arco = fila.amigos.obtenerPorIndice(j);
                int idAmigo = arco.id;
                int amistad = arco.amistad;
                    
                int indice = listaAd.obtenerIndiceUsuario(idAmigo);
                FilaAdyacencia fila2 = listaAd.obtenerFila(indice);
                Arco arco2 = fila2.amigos.obtener(idUsuario);
                
                arco.amistad *= -1;
                arco2.amistad *= -1;
                String islas2 = obtenerIslas("bfs");
                int nIslas2 = contarIslas(islas2);
                arco.amistad = amistad;
                arco2.amistad = amistad;
    
                if(idUsuario < idAmigo && nIslas2 - nIslas1 > 0){
                    puentes += Integer.toString(idUsuario) + ", " + Integer.toString(idAmigo) + ", " + Integer.toString(amistad) + "\n"; 
                }
            }
        }
        return puentes;
    }
   
    /**
    * Escribe los datos del grafo en el archivo escogido
    */
    public void guardarGrafo(String filename){
        try {
            FileWriter escritor = new FileWriter(filename);
            
            escritor.write("Usuarios\n");
            int n = listaAd.n;
            for(int i=0; i < n; i++){
                Usuario usuario = listaAd.obtenerFila(i).usuario;
                String linea = Integer.toString(usuario.id) + ", " + usuario.usuario + "\n";
                escritor.write(linea);
            }
            
            escritor.write("Relaciones\n");
            for(int i=0; i <  n; i++){
                FilaAdyacencia fila = listaAd.obtenerFila(i);
                int idUsuario =  fila.usuario.id;
                int nAmistades =  fila.amigos.n;
                for(int j=0; j < nAmistades; j++){
                    Arco arco = fila.amigos.obtenerPorIndice(j);
                    int idAmigo = arco.id;

                    if(idUsuario < idAmigo){
                        String linea = Integer.toString(idUsuario) + ", " + Integer.toString(idAmigo) + ", " + Integer.toString(arco.amistad)  + "\n";
                        escritor.write(linea);
                    }
                }
            }
            
            escritor.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
}
