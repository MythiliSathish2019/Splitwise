package com.Mythili.Splitwise.Service.Strategy;

import com.Mythili.Splitwise.Model.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MostEffectiveSettleUpStrategy implements SettleUpStrategy{

    @Override
    public List<SettlementTransaction> settleUp(Group group) {
        HashMap<User,Double> map=getBalances(group.getExpenses());
        //for borrowers
        PriorityQueue<UserAmount>negativeHeap=new PriorityQueue<>(new Comparator<UserAmount>() {
            @Override
            public int compare(UserAmount o1, UserAmount o2) {
                return (int)(o1.getAmount()-o2.getAmount());
            }
        });
        //for lenders
        PriorityQueue<UserAmount>positiveHeap=new PriorityQueue<>(new Comparator<UserAmount>() {
            @Override
            public int compare(UserAmount o1, UserAmount o2) {
                return (int)(o2.getAmount()-o1.getAmount());
            }
        });
        //adding userAmount in the respective priority queues
        for(User key: map.keySet()){
            double amount=map.get(key);
            if(amount>0)
                positiveHeap.add(new UserAmount(key,amount));
            else
                negativeHeap.add(new UserAmount(key,amount));
        }
        //creating settlement transactions
        List<SettlementTransaction> settlementTransactionList=new ArrayList<>();
        while(!positiveHeap.isEmpty() && !negativeHeap.isEmpty())
        {
            UserAmount l=positiveHeap.poll();
            UserAmount b=negativeHeap.poll();

            double difference=l.getAmount()+b.getAmount();
            SettlementTransaction settlementTransaction=new SettlementTransaction(b.getUser(),l.getUser());

            if(difference>0)
            {
                positiveHeap.add(new UserAmount(l.getUser(),difference));
                settlementTransaction.setAmount(l.getAmount());
            }
            else if(difference<0)
            {
                negativeHeap.add(new UserAmount(b.getUser(),difference));
                settlementTransaction.setAmount(-1*b.getAmount());
            }
            else
                settlementTransaction.setAmount(l.getAmount());
            settlementTransactionList.add(settlementTransaction);
        }

        return settlementTransactionList;
    }

    private HashMap<User,Double> getBalances (List<Expense>expenses){
        HashMap<User,Double> balanceMap=new HashMap<>();
        expenses.forEach(e->{
            List<UserExpense>userExpenseList=e.getUserExpenses();
            userExpenseList.forEach(userExpense -> {
                User user=userExpense.getUser();
                double amount=userExpense.getAmount();
                if (balanceMap.containsKey(user))
                {
                    if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID))
                        balanceMap.put(user,balanceMap.get(user)+amount);
                    else
                        balanceMap.put(user,balanceMap.get(user)-amount);
                }
                else
                {
                    if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID))
                        balanceMap.put(user,0+amount);
                    else
                        balanceMap.put(user,0-amount);
                }
            });
        });
        System.out.println();
        return balanceMap;
    }

}
