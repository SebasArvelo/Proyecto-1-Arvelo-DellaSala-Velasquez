/**
* Representa la amistad con un usuario 
* y es un como elemento de una lista de arcos, por lo que tiene 
* una referencia al siguiente amigo
*/
public class Arco {
    int amistad;
    int id;
    Arco next;
    
    public Arco(int a, int id) {
        amistad = a; 
        this.id = id;
        next = null;
    }
}