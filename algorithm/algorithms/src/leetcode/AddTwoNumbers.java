package leetcode;

public class AddTwoNumbers {
      public static class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }

          public ListNode(int i, ListNode num3) {
              val = i;
              next = num3;
          }

          public int getVal() {
              return val;
          }

          public void setVal(int val) {
              this.val = val;
          }

          public ListNode getNext() {
              return next;
          }

          public void setNext(ListNode next) {
              this.next = next;
          }
          public ListNode(){
              super();
          }
      }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    public static void main(String[] args) {
          ListNode num3 = new ListNode(3);
          ListNode num2 = new ListNode(4,num3);
          ListNode num1 = new ListNode(2,num2);
          ListNode num6 = new ListNode(4);
          ListNode num5 = new ListNode(6,num6);
          ListNode num4 = new ListNode(5,num5);
          ListNode result = new ListNode();
          result = addTwoNumbers(num1,num4);
          System.out.println(result.val);
    }
}
