package Width;

public class Struct {
    int num[] = new int[9];
    Struct parent;
    Struct next;

    @Override
    public boolean equals(Object obj){
        Boolean flag = true;
        if(obj instanceof Struct){
            Struct p = (Struct)obj;
            for (int i = 0; i < 9; i++){
                if (p.num[i] != this.num[i]){
                    flag = false;
                }
            }
        }
        else {
            flag = false;
        }
        return flag;
    }
}

