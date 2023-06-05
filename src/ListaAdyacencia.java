/**
* Representa una lista de adyacencia representación de un grafo
* está formada por FilaAdyacencia, que cada es un usuario 
*/
public class ListaAdyacencia {
    FilaAdyacencia head;
    int n = 0;
    
    public void agregarNodo(Usuario usuario){    
        if(head == null){
            head = new FilaAdyacencia(usuario);
        } else {
            FilaAdyacencia current = head;
            while(current.next != null)
                current = current.next;
            current.next = new FilaAdyacencia(usuario);
        }
        n++;
    }
    
    public void agregarArco(int id, int idAmigo, int amistad){
        if (head == null) {
            return;
        }
        
        FilaAdyacencia temp = head;
        while (temp != null) {
            if (temp.usuario.id == id) {
                temp.amigos.agregar(amistad, idAmigo);
            }
            temp = temp.next;
        }
    }
    
    public void eliminarNodo(int id){
        FilaAdyacencia current = head, prev = null;
 
        if (current!= null && current.usuario.id == id) {
            head = current.next; 
            n--;
            return;
        }
        
        while (current!= null && current.usuario.id != id) {
            prev = current;
            current = current.next;
        }
  
        if (current!= null) {
            prev.next = current.next;
            n--;
        }
    }
    
    /**
    * Obtiene el indice en la lista que tiene el usuario con la id presentada
    */
    public int obtenerIndiceUsuario(int id){
        if(head == null) 
            return -1;
        if(head.usuario.id == id)
            return 0;
        int index = 1;
        FilaAdyacencia current = head;
        while(current.next != null){
            if(current.next.usuario.id == id)
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }
    
    /**
    * Obtiene la fila en la posición indice requerida
    */
    public FilaAdyacencia obtenerFila(int indice){
        if(head == null)
            return null;
        if(indice == 0)
            return head;
        int i = 1;
        FilaAdyacencia current = head;
        while(current.next != null){
            if(i == indice)
                return current.next;
            current = current.next;
            i++;
        }
        return null;
    }

}
