package MountClimb;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MountClimb {

    Stack<Struct> open = new Stack<Struct>();
    Stack<Struct> closed = new Stack<Struct>();
    Stack<Struct>spring = new Stack<Struct>();
    List<Struct> result = new ArrayList<Struct>();

    int start[] = new int[9];
    int target[] = new int[9];

    int Postion;

    Struct structOfStart = new Struct();
    Struct structOfTarget = new Struct();

    public void init() {
        int i = 0;
        /*System.out.println("Enter the origin digits:");
        Scanner io = new Scanner(System.in);
        String s = io.nextLine();

        System.out.println("Enter the target digits:");


        Scanner io1 = new Scanner(System.in);
        String s1 = io1.nextLine();*/

        String s = "2 8 3 1 6 4 7 0 5";
        String s1 ="1 2 3 8 0 4 7 6 5";

        String string[] = s.split(" ");

        for (String st : string) {
            if (!st.equals("")) {
                start[i++] = Integer.parseInt(st);
            }
        }

        String string1[] = s1.split(" ");
        i = 0;

        for (String st : string1) {
            if (!st.equals("")) {
                target[i++] = Integer.parseInt(st);
            }
        }

        for (i = 0; i < 9; i++) {
            structOfStart.num[i] = start[i];
        }

        getPos(structOfStart);

        structOfStart.parent = null;
        structOfStart.next = null;
        open.add(structOfStart);

        for (i = 0; i < 9; i++) {
            structOfTarget.num[i] = target[i];
        }
    }

    public void getPos(Struct status) {
        int i;
        for (i = 0; i < 9; i++) {
            if (status.num[i] == 0){
                Postion = i;
            }
        }
    }

    public int Manhattan(Struct status) {  // 计算曼哈顿距离
        int sum = 0;
        int row = -1;
        int col = -1;
        int temp = -1;
        int distance = -1;

        for(int i = 0; i < 9; i++){
            temp = status.num[i];
            if (temp == 0){
                continue;
            }
            row = temp / 3;
            col = temp % 3;
            distance = Math.abs(row - i/3 ) + Math.abs(col - i%3);
            sum = sum + distance;
        }
        return sum;
    }

    public Boolean icansolve()
    {
        boolean flag = false;
        int i ,j;
        int resultOfStart=0;
        int resultOfTarget = 0;
        for(i=0;i<9;i++)
        {
            for(j=0;j<i;j++)
            {
                if(start[j]<start[i]&&start[j]!=0)
                    resultOfStart++;
                if(target[j]<target[i]&&target[j]!=0)
                    resultOfTarget++;
            }
        }
        if((resultOfStart+resultOfTarget)%2==0)
            flag=true;
        return flag;
    }


    public Boolean hasSameStatus(Struct s1, Struct s2) {
        boolean flag = true;
        for (int i = 0; i < 9; i++) {
            if (s1.num[i] != s2.num[i])
                flag = false;
        }
        return flag;
    }

    public Boolean sameStatus(Struct origin){
        for (int i = 0; i < result.size(); i++){
            if (hasSameStatus(origin,result.get(i))){
                return true;
            }
        }
        return false;
    }

    public void copySnumToTnum(int a[], int b[]) {
        int len = b.length;
        for (int i = 0; i < len; i++) {
            a[i] = b[i];
        }
    }

    public void getShift(Struct status, int index, int pos) {
        int medium = 0;
        Struct temp = new Struct();

        copySnumToTnum(temp.num, status.num);

        if (index == 1) {
            //交换位置
            medium = temp.num[pos];
            temp.num[pos] = temp.num[pos - 1];
            temp.num[pos - 1] = medium;

        } else if (index == 2) {
            //交换位置
            medium = temp.num[pos];
            temp.num[pos] = temp.num[pos - 3];
            temp.num[pos - 3] = medium;

        } else if (index == 3) {
            //交换位置
            medium = temp.num[pos];
            temp.num[pos] = temp.num[pos + 1];
            temp.num[pos + 1] = medium;

        } else {
            //交换位置
            medium = temp.num[pos];
            temp.num[pos] = temp.num[pos + 3];
            temp.num[pos + 3] = medium;
        }

        temp.parent = status;
        temp.next = null;
        spring.push(temp);
        getPos(temp);
    }



    public boolean hillClimbing(Struct status){
        int curManht;
        int minManht;
        int nextManht;
        Struct struct;

        int minPos;

        for (int trial = 0; trial < 200; trial++) {
            curManht = Manhattan(status);
            minManht = 99999;
            minPos = 0;
            for (int i = 1; i < 5; i++){
                struct = status;
                if((Postion%3!=0 && i == 1)||(Postion > 2 && i == 2) ||
                        (Postion % 3!= 2 && i ==3) || (Postion < 6 &&i == 4))
                {
                    getShift(status,i,Postion);
                }

                nextManht = Manhattan(spring.peek());
                if (nextManht < minManht){
                    minManht = nextManht;
                    minPos = Postion;
                }

                getPos(status);

                status = struct;

            }

            if (curManht > minManht){
                status = spring.peek();
                Postion = minPos;
            }

            if (hasSameStatus(status,structOfTarget)){
                return true;
            }

        }
        return false;
    }

    public void resolve(){
        int numcount = 1;
        Struct getOfOpen = null;
        boolean flag = false;
        init();

        if (!icansolve()) {
            System.out.println("不能解决！！");
            System.exit(0);
        }

        System.out.println("从表中拿出的结点的状态及相应的值:");

        getOfOpen = open.peek();

        boolean find = false;

        int count = 0;
        while (!find && count < 200) {
            count++;
            find = hillClimbing(getOfOpen);
        }
        if(count == 200){
            System.out.println("路径错误，无法找到正确答案");
        }

    }

}
