
package casino;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class Hand implements Comparable{

    private Card[]hand=new Card[5];
    
    public Hand() {
        hand[0]=new Card(0,"empty");
        hand[1]=new Card(0,"empty");
        hand[2]=new Card(0,"empty");
        hand[3]=new Card(0,"empty");
        hand[4]=new Card(0,"empty");
    }
    
    public Hand(Card[]cards){
        this.hand=Arrays.copyOf(cards, cards.length);
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }
    
    

    
    
    public int handValue(){
        for (Card card : hand) {
            if(card.getValue()==0){
                return -1;
            }
        }
        Arrays.sort(hand);
        


        int handValue=0;
        Arrays.sort(hand);
        if(checkRoyalFlush()){
            handValue=9;
            return handValue;
        }
  
        else if (checkStraight()&&checkFlush()){
            handValue=8;
            return handValue;
        }
        else if(checkFourKind()){
            handValue=7;
            return handValue;
        }
        else if(checkFullHouse()){
            handValue=6;
            return handValue;
        }
        else if(checkFlush()){
            handValue=5;
            return handValue;
        }
        else if(checkStraight()){
            handValue=4;
            return handValue;
        }
        else if (checkThreeKind(0)){
            handValue=3;
            return handValue;
        }
       
        else if (checkTwoPair()){
            handValue=2;
            return handValue;
        }
        else if(checkPair()){
            handValue=1;
            return handValue;
        }  
        return handValue;
    }
    
    private boolean checkPair(){
        for (int i =0;i<hand.length;i++) {
            for (int j =0;j<hand.length;j++) {
                if(hand[i].getValue()==hand[j].getValue()&&!(hand[i].equals(hand[j]))){
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean checkTwoPair(){
        int numPairs = 0;
        
        for (int i =0;i<hand.length-1;i++) {
            if(hand[i].getValue()==hand[i+1].getValue()){
                numPairs+=1;
                i+=1;
            }
        }
        if(numPairs==2){
            return true;
        }
        return false;
    }
    
    private boolean checkThreeKind(int start){
        int numSame=1;
        if(start==hand.length-2){
            return false;
        }
        for(int i=start+1;i<hand.length;i++){
            if(hand[i].getValue()==hand[start].getValue()){
                numSame+=1;
            }
        }
        if(numSame==3){
            return true;
        }
        return checkThreeKind(start+1);
    }
    
    private boolean checkStraight(){
        if(hand[1].getValue()==hand[0].getValue()-1&&hand[2].getValue()==hand[0].getValue()-2&&hand[3].getValue()==hand[0].getValue()-3&&hand[4].getValue()==hand[0].getValue()-4){
            return true;
        }
        else if(hand[0].getValue()==1){
            if(hand[1].getValue()==13&&hand[2].getValue()==12&&hand[3].getValue()==11&&hand[4].getValue()==10){
                return true;
            }
            else if(hand[1].getValue()==5&&hand[2].getValue()==4&&hand[3].getValue()==3&&hand[4].getValue()==2){
                return true;
            }
        }
        return false;
    }
    
    private boolean checkFlush(){
        String suit = hand[0].getSuit();
        for(Card card : hand){
            if(!(card.getSuit().equals(suit))){
                return false;
            }
        }
        return true;
    }
    
    private boolean checkFullHouse(){
        boolean triple=false,pair=false;
        int valueOfTrip=0;
        for(int i =0;i<hand.length-2;i++){
            if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()==hand[i+2].getValue()){
                triple=true;
                valueOfTrip=hand[i].getValue();
            }
        }
        for(int i =0;i<hand.length-1;i++){
            if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()!=valueOfTrip){
                pair=true;
            }
        }
        
        if(triple&&pair){
            return true;
        }
        return false;
    }
    
    private boolean checkFourKind(){
        for(int i =0;i<hand.length-3;i++){
            if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()==hand[i+2].getValue()&&hand[i].getValue()==hand[i+3].getValue()){
                return true;
            }
        }
        return false;
    }
    
    private boolean checkRoyalFlush(){
        boolean straight = false;
        boolean flush = true;
        
        if(hand[0].getValue()==1&&hand[1].getValue()==13&&hand[2].getValue()==12&&hand[3].getValue()==11&&hand[4].getValue()==10){
             straight = true;
        }
        
        String suit = hand[0].getSuit();
        for(Card card : hand){
            if(!(card.getSuit().equals(suit))){
                flush = false;
            }
        }
        if(flush&&straight){
            return true;
        }
        return false;
        
    }

    @Override
    public int compareTo(Object t) {
        Hand secondHand = (Hand)t;
        if(secondHand.handValue()>handValue()){
            return 1;
        }
        else if(secondHand.handValue()<handValue()){
            return -1;
        }
        else{
            return tieBreaking(secondHand);
        }
    }
    
    public static Hand copyOf(Hand hand){
        Hand copy=new Hand(hand.getHand());
        return copy;
    }
    
    public int tieBreaking(Hand secondHand){
        Arrays.sort(hand);
        Arrays.sort(secondHand.getHand());
        if(handValue()==0){
            for(int i=0;i<hand.length;i++){
                if((secondHand.getHand()[i].getValue()>hand[i].getValue()||secondHand.getHand()[i].getValue()==1)&&hand[i].getValue()!=1){
                    return 1;
                }
                else if((secondHand.getHand()[i].getValue()<hand[i].getValue()||hand[i].getValue()==1)&&secondHand.getHand()[i].getValue()!=1){
                    return -1;
                }
            }
        }
        if(handValue()==1){
            int pairValue=0,secondPairValue=0;
            for(int i =0;i<hand.length-1;i++){
                if(hand[i].getValue()==hand[i+1].getValue()){
                    pairValue=hand[i].getValue();
                    break;
                }
            }
            for(int i =0;i<secondHand.getHand().length-1;i++){
                if(secondHand.getHand()[i].getValue()==secondHand.getHand()[i+1].getValue()){
                    secondPairValue=secondHand.getHand()[i].getValue();
                    break;
                }
            }
            if(secondPairValue>pairValue){
                return 1;
            }
            else if(secondPairValue<pairValue){
                return -1;
            }
            else{
                for(int i =0;i<hand.length;i++){
                   if((secondHand.getHand()[i].getValue()>hand[i].getValue()||secondHand.getHand()[i].getValue()==1)&&hand[i].getValue()!=1){
                       return 1;
                   }
                   else if((secondHand.getHand()[i].getValue()<hand[i].getValue()||hand[i].getValue()==1)&&secondHand.getHand()[i].getValue()!=1){
                       return -1;
                   }
                }
               
                return 0;
            }
        }
        else if(handValue()==2){
            int pairValue=0,secondPairValue=0, secondHandPairValue=0,secondHandSecondPairValue=0;
            for(int i =0;i<hand.length-1;i++){
                if(hand[i].getValue()==hand[i+1].getValue()){
                    pairValue=hand[i].getValue();
                    break;
                }
            }
            for(int i =0;i<hand.length-1;i++){
                if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()!=pairValue){
                    secondPairValue=hand[i].getValue();
                    break;
                }
            }
            for(int i =0;i<secondHand.getHand().length-1;i++){
                if(secondHand.getHand()[i].getValue()==secondHand.getHand()[i+1].getValue()){
                    secondHandPairValue=secondHand.getHand()[i].getValue();
                    break;
                }
            }
            for(int i =0;i<secondHand.getHand().length-1;i++){
                if(secondHand.getHand()[i].getValue()==secondHand.getHand()[i+1].getValue()&&secondHand.getHand()[i].getValue()!=secondHandPairValue){
                    secondHandSecondPairValue=secondHand.getHand()[i].getValue();
                    break;
                }
            }
            
            if(secondHandPairValue>pairValue){
                return 1;
            }
            else if(secondHandPairValue<pairValue){
                return -1;
            }
            else{
                if(secondHandSecondPairValue>secondPairValue){
                    return 1;
                }
                else if(secondHandSecondPairValue<secondPairValue){
                    return -1;
                }
                else{
                    int kicker=0,secondKicker=0;
                    for(int i =0;i<hand.length;i++){
                        if(hand[i].getValue()!=pairValue&&hand[i].getValue()!=secondPairValue){
                            kicker = hand[i].getValue();
                        }
                        if(secondHand.getHand()[i].getValue()!=pairValue&&secondHand.getHand()[i].getValue()!=secondPairValue){
                            secondKicker = secondHand.getHand()[i].getValue();
                        }
                    }
                    if((secondKicker>kicker||secondKicker==1)&&kicker!=1){
                        return 1;
                    }
                    else if((secondKicker<kicker||kicker==1)&&secondKicker!=1){
                        return -1;
                    }
                    else{
                        return 0;
                    }
                }
            }

        }
        else if(handValue()==3){
            int tripValue=0,secondTripValue=0;
            for(int i =0;i<hand.length-2;i++){
                if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()==hand[i+2].getValue()){
                    tripValue=hand[i].getValue();
                    break;
                }
            }
            for(int i =0;i<secondHand.getHand().length-2;i++){
                if(secondHand.getHand()[i].getValue()==secondHand.getHand()[i+1].getValue()&&secondHand.getHand()[i].getValue()==secondHand.getHand()[i+2].getValue()){
                    secondTripValue=secondHand.getHand()[i].getValue();
                    break;
                }
            }
            if(secondTripValue>tripValue){
                return 1;
            }
            else if(secondTripValue<tripValue){
                return -1;
            }
            else{
                for(int i =0;i<hand.length;i++){
                   if((secondHand.getHand()[i].getValue()>hand[i].getValue()||secondHand.getHand()[i].getValue()==1)&&hand[i].getValue()!=1){
                       return 1;
                   }
                   else if((secondHand.getHand()[i].getValue()<hand[i].getValue()||hand[i].getValue()==1)&&secondHand.getHand()[i].getValue()!=1){
                       return -1;
                   }
                }
                return 0;
            }
        }
        else if(handValue()==4||handValue()==8){
            if((hand[0].getValue()<secondHand.getHand()[0].getValue()||secondHand.getHand()[0].getValue()==1)&&hand[0].getValue()!=1){
                return 1;
            }
            else if((hand[0].getValue()>secondHand.getHand()[0].getValue()||hand[0].getValue()==1)&&secondHand.getHand()[0].getValue()!=1 ){
                return -1;
            }
            else{
                return 0;
            }
        }
        else if(handValue()==5){
            for(int i =0;i<hand.length;i++){
                if((secondHand.getHand()[i].getValue()>hand[i].getValue()||secondHand.getHand()[i].getValue()==1)&&hand[i].getValue()!=1){
                    return 1;
                }
                else if((secondHand.getHand()[i].getValue()<hand[i].getValue()||hand[i].getValue()==1)&&secondHand.getHand()[i].getValue()!=1){
                    return -1;
                }
            }
               
            return 0;
        }
        else if(handValue()==6){
            int pairValue=0,tripValue=0, secondHandPairValue=0,secondHandTripValue=0;
            for(int i =0;i<hand.length-2;i++){
                if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()==hand[i+2].getValue()){
                    tripValue=hand[i].getValue();
                    break;
                }
            }
            for(int i =0;i<hand.length-1;i++){
                if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()!=tripValue){
                    pairValue=hand[i].getValue();
                    break;
                }
            }
            for(int i =0;i<secondHand.getHand().length-2;i++){
                if(secondHand.getHand()[i].getValue()==secondHand.getHand()[i+1].getValue()&&secondHand.getHand()[i].getValue()==secondHand.getHand()[i+2].getValue()){
                    secondHandTripValue=secondHand.getHand()[i].getValue();
                    break;
                }
            }
            for(int i =0;i<secondHand.getHand().length-1;i++){
                if(secondHand.getHand()[i].getValue()==secondHand.getHand()[i+1].getValue()&&secondHand.getHand()[i].getValue()!=secondHandTripValue){
                    secondHandPairValue=secondHand.getHand()[i].getValue();
                    break;
                }
            }
            if(secondHandTripValue>tripValue){
                return 1;
            }
            else if(secondHandTripValue<tripValue){
                return -1;
            }
            else{
                if(secondHandPairValue>pairValue){
                    return 1;
                }
                else if(secondHandPairValue<pairValue){
                    return -1;
                }
                else{
                    return 0;
                }
            }
                
        }
        else if(handValue()==7){
            int quadValue=0,secondQuadValue=0;
            for(int i =0;i<hand.length-3;i++){
                if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()==hand[i+2].getValue()&&hand[i].getValue()==hand[i+3].getValue()){
                    quadValue=hand[i].getValue();
                    break;
                }
            }
            for(int i =0;i<secondHand.getHand().length-2;i++){
                if(secondHand.getHand()[i].getValue()==secondHand.getHand()[i+1].getValue()&&secondHand.getHand()[i].getValue()==secondHand.getHand()[i+2].getValue()&&secondHand.getHand()[i].getValue()==secondHand.getHand()[i+3].getValue()){
                    secondQuadValue=secondHand.getHand()[i].getValue();
                    break;
                }
            }
            if(secondQuadValue>quadValue){
                return 1;
            }
            else if(secondQuadValue<quadValue){
                return -1;
            }
            else{
                for(int i =0;i<hand.length;i++){
                   if((secondHand.getHand()[i].getValue()<hand[i].getValue()||secondHand.getHand()[i].getValue()==1)&&hand[i].getValue()!=1){
                       return 1;
                   }
                   else if((secondHand.getHand()[i].getValue()<hand[i].getValue()||hand[i].getValue()==1)&&secondHand.getHand()[i].getValue()!=1){
                       return -1;
                   }
                }
                return 0;
            }
            
        }

        return 0;

    }
}
