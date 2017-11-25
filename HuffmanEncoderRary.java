   import java.util.Random;
   import java.util.ArrayList;
   import java.util.Scanner;
   import java.io.File;
   import java.io.FileNotFoundException;

   public class HuffmanEncoderRary
   {
   private static double entropy;
   private static double averageCodeLength;
   
      public static void main(String[] args) throws FileNotFoundException
      {  
         ArrayList<HuffmanNodeRary> uncoded = new ArrayList<HuffmanNodeRary>();
         //ArrayList<SymbolNode>  symbolTable = new ArrayList<SymbolNode>();
         //HuffmanTree hTree = new HuffmanTree();
         Scanner keyboard = new Scanner(System.in);
			
         System.out.print("Input File name: ");
         String inputFileName = keyboard.next(); // get NAME of input file
         //System.out.print("Output File name: ");
         //String outputFileName = keyboard.next();// get NAME of output file
      
         File inputFile = new File(inputFileName); // pointer to  input FILE      
         Scanner inFile = new Scanner(inputFile);  // Scanner for input FILE
         inFile.useDelimiter("[^A-za-z0-9 ']+");    // 
         
         System.out.println("Enter symbol length: ");
         int symbolLength = keyboard.nextInt();
         entropy = 0.0;
         averageCodeLength = 0.0;

// Part 1:  Get symbols and their probabilities from the screen, then 
//          insert them, in increasing order or probabilties, into the uncoded list.         
         while (inFile.hasNextLine())
         {
             String nextLine = inFile.nextLine();
             Scanner SandPScanner = new Scanner(nextLine);
             System.out.println(nextLine);
             SandPScanner.useDelimiter(",");
             String s = SandPScanner.next();
             String pString = SandPScanner.next();
             double p = Double.parseDouble(pString);
             System.out.println("   " + s + "   " + p);
             HuffmanNodeRary hn = new HuffmanNodeRary(s,p, null);
             if (uncoded.size() == 0)
                uncoded.add(hn);
             else
             {
                int length = uncoded.size();
                if (p > uncoded.get(length-1).getProbability())
                   uncoded.add(hn);
                else
                {
                   int i = length - 1;
                   while (p < uncoded.get(i).getProbability() && i > 0)     i--;
                   if (i == 0 && p < uncoded.get(i).getProbability())
                      uncoded.add(0,hn);
                   else if (i == 0 && p >= uncoded.get(i).getProbability())
                      uncoded.add(1,hn);
                   else 
                      uncoded.add(i+1,hn);
                      
                }
             } 
           }
          inFile.close();
         //outputFile.close(); 
         
       //System.out.println("The uncoded array " + uncoded);
// Print out the entire uncoded array to see that it's "there"!       
         
         
         int once = 1;
         System.out.print("Input r: ");
         int r = keyboard.nextInt();
         int firststep = ((uncoded.size()-2)%(r-1))+2; //# of 1st combined nodes
         
         while (uncoded.size() > 1)
         {   
           //System.out.println("uncoded size " + uncoded.size());
            if(once == 1)
            {
               double probsum = 0;
               ArrayList<HuffmanNodeRary> HuffStart = new ArrayList<HuffmanNodeRary>();
               for(int z = 0; z<firststep;z++)
               {
                  HuffStart.add(uncoded.get(0));
                  probsum += uncoded.get(0).getProbability();
                  uncoded.remove(0);
               }

               HuffmanNodeRary hn = new HuffmanNodeRary("",probsum,HuffStart); 
               if (uncoded.size() == 0) 
                uncoded.add(hn);
               else
               {
                int length = uncoded.size();
                double p = probsum;
                if (p > uncoded.get(length-1).getProbability())
                   uncoded.add(hn);
                else
                {
                   int i = length - 1;
                   while (p < uncoded.get(i).getProbability() && i > 0) i--;
                   if (i == 0 && p < uncoded.get(i).getProbability())
                      uncoded.add(0,hn);
                   else if (i == 0 && p >= uncoded.get(i).getProbability())
                      uncoded.add(1,hn);
                   else 
                      uncoded.add(i+1,hn);
                }
               
               
               once+=1;
               
               //this creates our first combined node
               }
            }
            else{
            
             ArrayList<HuffmanNodeRary> HuffNext = new ArrayList<HuffmanNodeRary>();
             double probsum = 0;
             
             for(int pz = 0; pz<r;pz++)
             {
               HuffNext.add(uncoded.get(0));
               ///now we need to recursively combine probabilities
               probsum+= getProb(uncoded.get(0));
               uncoded.remove(0);
             }
             
             HuffmanNodeRary hn = new HuffmanNodeRary("", probsum,HuffNext);
             
             if (uncoded.size() == 0) 
                uncoded.add(hn);
             else
             {
                int length = uncoded.size();
                double p = probsum;
                if (p > uncoded.get(length-1).getProbability())
                   uncoded.add(hn);
                else
                {
                   int i = length - 1;
                   while (p < uncoded.get(i).getProbability() && i > 0)     i--;
                   if (i == 0 && p < uncoded.get(i).getProbability())
                      uncoded.add(0,hn);
                   else if (i == 0 && p >= uncoded.get(i).getProbability())
                      uncoded.add(1,hn);
                   else 
                      uncoded.add(i+1,hn);
                }
             }            
         }
         
         }
         
         addCodes(uncoded.get(0), "");
         displayTree(uncoded.get(0));
         System.out.println();
         System.out.println("Entropy:               " + entropy);
         System.out.println("AvgCodeLen:            " + averageCodeLength);
         System.out.println("AvgCodeLen / symbol:   " + averageCodeLength / symbolLength);
      }

      public static void addCodes(HuffmanNodeRary t, String s)
      {
         ArrayList<HuffmanNodeRary> A = t.getArray();
         
         if(A==null)
            {
               t.setCode(s);
            }
         else{
               for(int i = 0; i<A.size();i++)
               {
                  addCodes(A.get(i),s.concat(Integer.toString(i)));
               }
         }
      }
      // Recursively display the tree
      public static void displayTree(HuffmanNodeRary t)
      {
         ArrayList<HuffmanNodeRary> A = t.getArray();
         
         if (A != null )
         {
          for(int i = 0; i<A.size();i++)
          {
            displayTree(A.get(i));
            if (t.getSymbol().length() != 0)
            {
                System.out.printf("%2s  %-12s  %10.5f%n", t.getSymbol(), t.getCode(), t.getProbability());
                entropy -= t.getProbability() * Math.log(t.getProbability())/Math.log(2.0);
                averageCodeLength += t.getProbability() * t.getCode().length();
            }
         }
         }
         else{
               System.out.printf("%2s  %-12s  %10.5f%n", t.getSymbol(), t.getCode(), t.getProbability());
               entropy -= t.getProbability() * Math.log(t.getProbability())/Math.log(2.0);
               averageCodeLength += t.getProbability() * t.getCode().length();
         }
      }
      
      public static double getProb(HuffmanNodeRary h)
      {
         double p=0;
         double prob=0;
         ArrayList<HuffmanNodeRary> A = h.getArray();
         
         if(A==null)
            {
               p=h.getProbability();
               return p;
            }
         
         else{
               for(int i=0;i<A.size();i++)
               {
                  prob+=getProb(A.get(i));
               }
                  return prob; 
             }
      
      }

   }

