import java.util.*;

   public class HuffmanNodeRary
   {
      //SymbolNode sNode;
      String symbol;
		double probability;
      String code;
      int codeLength;
      int cumsum;
      ArrayList<HuffmanNodeRary> nodes;
   
      // HuffmanNode(SymbolNode sN, HuffmanNode lt,  HuffmanNode rt)
      HuffmanNodeRary(String s, double p, ArrayList<HuffmanNodeRary> nodelist)
      {
         //sNode = sN;
         symbol = s;
         probability = p;
         nodes = nodelist;
         
      }
   
      HuffmanNodeRary()
      {
      }
            
      void setCode(String c)
      {
         code = c;
         codeLength = c.length();
      }
      
      void setProbability(double p)
      {
         probability = p;
      }
      
      String getSymbol()
      {
         return symbol;
      } 
       
      double getProbability()
      {
         return probability;
      }  
      
      String getCode()
      {
         return code;
      }  
      
      int getCodeLength()
      {
         return codeLength;
      }  
      
      ArrayList<HuffmanNodeRary> getArray()
      {
         return nodes;
      }
      
 
   }
