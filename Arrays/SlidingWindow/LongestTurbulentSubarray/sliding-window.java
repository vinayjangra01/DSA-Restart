
class Solution {
    public int maxTurbulenceSize(int[] arr) {
        //[9,4,2,10,7,8,8,1,9]
        int i = 0;
        int j = 1;
        int maxLength = 1;
        int n = arr.length;
        int prevSign = 0; //1>, -1<, 0=, The first sign should not force a false pattern break.
        //window = [i, j)
        //size = j - i

        while(j < n)
        {
            int currSign = Integer.compare(arr[j], arr[j - 1]);
            
            if(currSign == 0)
            {
                maxLength = Math.max(j - i, maxLength);
                i = j;
            }//no need to handle the first case seperately, just make sure it don't break the condition, as it's always valid, if just sign is not =
            else if(!(prevSign == 1 && currSign == -1 || prevSign == -1 && currSign == 1))
{
                maxLength = Math.max(j - i, maxLength);
                i = j - 1;
            }
            prevSign = currSign;
            j++;
        }


        maxLength = Math.max(j - i, maxLength);
        

        return maxLength;
    }
}
