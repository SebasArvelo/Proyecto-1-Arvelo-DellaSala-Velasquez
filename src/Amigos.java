/**
* Es una lista de arcos representando los amigos de un usuario
*/
public class Amigos {
    Arco head;
    int n = 0;
    
    public void agregar(int amistad, int id){
        if(head == null){
            head = new Arco(amistad, id);
        } else {
            Arco actual = head;
            while(actual.next != null)
                actual = actual.next;
            actual.next = new Arco(amistad, id);
        }
        n++;
    }

    public Arco obtener(int id){
        if(head == null){
            return null;
        }
        if(head.id == id){
            return head;
        } else {
            Arco actual = head;
            while(actual.next != null){
                if(actual.next.id == id)
                    return actual.next;
                actual = actual.next;
            }
            return null;
        }
    }
    
    public Arco obtenerPorIndice(int indice){
        if(head == null)
            return null;
        if(indice == 0)
            return head;
        int i = 1;
        Arco actual = head;
        while(actual.next != null){
            if(i == indice)
                return actual.next;
            actual = actual.next;
            i++;
        }
        return null;
    }
     
    public void eliminar(int id){
        if(head == null)
            return;
        else if(head.id == id){
            head = head.next;
            n--;
            return;
        }
        else {
            Arco actual = head;
            while(actual.next != null){
                if(actual.next.id == id){
                    actual.next = actual.next.next;
                    n--;
                    return;
                }
                actual = actual.next;
            }
        }
    }
}
