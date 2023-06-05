/**
* Representa una fila en la lista de adyacencia, contiene
* una referencia a un usuario, sus amigos y la siguiente fila de la lista
*/
public class FilaAdyacencia {
    Usuario usuario;
    Amigos amigos;
    FilaAdyacencia next;
    
    public FilaAdyacencia(Usuario usuario){
        this.usuario = usuario;
        amigos = new Amigos();
        next = null;
    }
    
    public void eliminarAmigo(int id){
        Arco actual = amigos.head, anterior = null;
 
        if (actual!= null && actual.id == id) {
            amigos.head = actual.next; 
            amigos.n--;
            return;
        }
        
        while (actual!= null && actual.id != id) {
            anterior = actual;
            actual = actual.next;
        }
  
        if (actual!= null) {
            anterior.next = actual.next;
            amigos.n--;
        }
    }
}
