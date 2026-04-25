package producto;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static int[] calculo (int[] num){
        int n = num.length;
        int [] res = new int[n];

        res[0] = 1;
        for(int i = 1; i < n; i++){
            res[i] = res[i -1] * num[i - 1];
        }

        int right = 1;
        for(int i = n - 1; i >= 0; i--){
            res[i] = res[i] * right;
            right *= num[1];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int[] resultado = calculo(nums);

        for(int num: resultado){
            System.out.println(num + " ");
        }

    }
}