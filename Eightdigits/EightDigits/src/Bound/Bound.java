package Bound;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Bound {
    Stack<Struct> open = new Stack<Struct>();
    Stack<Struct> closed = new Stack<Struct>();
    Stack<Struct>spring = new Stack<Struct>();
    List<Struct> result = new ArrayList<Struct>();

    int start[] = new int[9];
    int target[] = new int[9];

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

        structOfStart.gvalue = 0;
        structOfStart.parent = null;
        structOfStart.next = null;
        open.add(structOfStart);

        for (i = 0; i < 9; i++) {
            structOfTarget.num[i] = target[i];
        }
    }


    public void add(Struct status, List<Struct> list) {
        list.add(status);
    }

    public Boolean hasSameStatus(Struct s1, Struct s2) {
        boolean flag = true;
        for (int i = 0; i < 9; i++) {
            if (s1.num[i] != s2.num[i])
                flag = false;
        }
        return flag;
    }

    public Boolean hasAnceSameStatus(Struct origin, Struct ancester) {
        boolean flag = false;
        while (ancester != null) {
            if (hasSameStatus(origin, ancester)) {
                flag = true;
                return flag;
            }
            ancester = ancester.parent;//寻找祖先结点
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


        if (!sameStatus(temp)){
            temp.parent = status;
            temp.next = null;
            temp.gvalue = status.gvalue + 1;
            spring.push(temp);
        }
    }

    public void depthSearch(Struct status){
        int pos = 0;
        boolean isExisted = false;

        spring.push(open.peek());


        while(!spring.empty()){
            Struct tp = spring.pop();
            result.add(tp);

            if(tp.gvalue > 49999){
                break;
            }

            if(hasSameStatus(structOfTarget,tp)){
                isExisted = true;
                break;
            }else {

                for(int i=0;i<9;i++)
                {
                    if(tp.num[i]==0)
                    {
                        pos=i;
                        break;
                    }
                }
                //右移
                if(pos%3!=0)
                {
                    getShift(tp,1,pos);
                }
                //下移
                if(pos>2)
                {
                    getShift(tp, 2, pos);
                }
                //左移
                if(pos%3!=2){
                    getShift(tp, 3, pos);
                }
                //上移
                if(pos<6)
                {
                    getShift(tp, 4, pos);
                }
            }
        }

        if(!isExisted){
            System.out.println("深度过浅无法得到结果");
            System.exit(0);
        }
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

    public void outputStatus(Struct status)
    {
        System.out.println("该状态位于第"+ status.gvalue+ "层");
        for(int i = 0;i<status.num.length;i++)
        {
            if(i%3==0)
                System.out.println();
            System.out.print(status.num[i]+" ");
        }
        System.out.println();
    }

    public void resolve() {
        try {
            PrintStream out = System.out;// 保存原输出流
            PrintStream ps=new PrintStream("/Users/renyuan/Desktop/EightDigits/src/BoundResult.txt");// 创建文件输出流1

            System.setOut(ps);// 设置使用新的输出流
            System.out.println("使用新的输出流将log输出到txt");
            int numcount = 1;
            Struct getOfOpen = null;
            boolean flag = false;
            init();

            if (!icansolve()) {
                System.out.println("不能解决！！");
                System.exit(0);
            }

            System.out.println("从表中拿出的结点的状态及相应的值:");

            getOfOpen = open.get(0);
            closed.add(getOfOpen);
            //open.remove(0);

            depthSearch(getOfOpen);

            Struct struct;

            while (!result.isEmpty()){
                struct = result.get(0);
                System.out.println("第" + numcount++ + "个状态是:");
                outputStatus(struct);
                result.remove(struct);
            }

            if(flag)
            {
                System.out.println("*************************************");

            }
            System.setOut(out);// 恢复原有输出流
            System.out.println("程序运行完毕，恢复为原输出流。");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
