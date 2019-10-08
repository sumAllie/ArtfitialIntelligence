package Width;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Width {
    List<Struct> open = new ArrayList<Struct>();
    List<Struct> closed = new ArrayList<Struct>();
    List<Struct> spring = new ArrayList<Struct>();

    int start[] = new int[9];
    int target[] = new int[9];

    Struct structOfStart = new Struct();
    Struct structOfTarget = new Struct();

    public void init() {
        int i = 0;
        System.out.println("Enter the origin digits:");
        Scanner io = new Scanner(System.in);
        String s = io.nextLine();

        System.out.println("Enter the target digits:");


        Scanner io1 = new Scanner(System.in);
        String s1 = io1.nextLine();

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

        if (!hasAnceSameStatus(temp, status.parent)) {
            temp.parent = status;
            temp.next = null;
            spring.add(0, temp);
        }
    }

    public void getNexts(Struct status)
    {
        int pos = 0;
        int i;
        //找到空格位置
        for(i=0;i<9;i++)
        {
            if(status.num[i]==0)
            {
                pos=i;
                break;
            }
        }
        //右移
        if(pos%3!=0)
        {
            getShift(status,1,pos);
        }
        //下移
        if(pos>2)
        {
            getShift(status, 2, pos);
        }
        //左移
        if(pos%3!=2){
            getShift(status, 3, pos);
        }
        //上移
        if(pos<6)
        {
            getShift(status, 4, pos);
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
        for(int i = 0;i<status.num.length;i++)
        {
            if(i%3==0)
                System.out.println();
            System.out.print(status.num[i]+" ");
        }
        System.out.println();
    }

    public void resolve() {
        int numcount = 1;
        Struct getOfOpen = null;
        boolean flag = false;
        init();

        if (!icansolve()) {
            System.out.println("不能解决！！");
            System.exit(0);
        }

        System.out.println("从表中拿出的结点的状态及相应的值:");

        while (!open.isEmpty()) {
            getOfOpen = open.get(0);
            closed.add(getOfOpen);
            open.remove(0);

            System.out.println("第" + numcount++ + "个状态是:");
            outputStatus(getOfOpen);

            if (hasSameStatus(getOfOpen, structOfTarget)) {
                flag = true;
                break;
            }

            getNexts(getOfOpen);

            while (!spring.isEmpty()) {
                //得到spring表中的结点
                Struct struct = spring.get(0);
                if (open.contains(struct)) {
                    //得到open表中相同的结点,注意这里重写了equals和hashcode方法
                    Struct structInOpen = open.get(open.indexOf(struct));
                    //改变open表中节点的parent指针及相关参数
                    structInOpen.parent = struct.parent;

                    spring.remove(struct);
                } else if (closed.contains(struct)) {
                    //得到closed表中相同的结点,注意这里重写了equals和hashcode方法
                    Struct structInClosed = closed.get(closed.indexOf(struct));
                    //改变closed表中节点的parent指针及相关参数
                    structInClosed.parent = struct.parent;
                    add(structInClosed, open);

                    //删除spring表中的该节点
                    spring.remove(struct);
                } else {
                    add(struct, open);
                    spring.remove(struct);
                }
            }


        }

        if(flag)
        {
            System.out.println("*************************************");

        }

    }
}
