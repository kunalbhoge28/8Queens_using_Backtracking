import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/*
 *
 * @author Kunal Bhoge
 */

public class EightQueens 
{
    boolean solution_found=false;
    int grid[][]=new int[8][8];

    List<Integer> list=new ArrayList<Integer>();
    Stack<Integer> stack=new Stack<Integer>();
    
  
    void run()
    {
        Random r=new Random(); 
        int starting_col=r.nextInt(8); 
    
        grid[0][starting_col]=1; //putting queen at the position of a column of 1st row randomly
        list.add(0);
        list.add(starting_col);
        stack.push(0);
        stack.push(starting_col);
      
        for(int i=1;i<8;i++) //starting with next row i.e 2nd row
        {
            int count=0;
            for(int j=0;j<8;j++)
            {
                if(checkValidity(i,j)== true && checkDiagonal2(i,j)==true && visitedRowCol(j, j)==true)
                {
                   grid[i][j]=1;
                   list.add(i);
                   list.add(j);
                   stack.push(i);
                   stack.push(j);
                   
                   //Placed Queens are stored in stack n list as [x1,y1,x2,y2,x3,y3....]
                   System.out.println(i+"th PASS Queens Placed At : "+stack);
                   
                   display();
                   break;//goes to next row now...breaking inner loop
                }
                else
                {
                   grid[i][j]=0;
                   count++; //counting 0s for each row
                }      
            }
            //if no place is found for queen to be placed in a row i.e num of 0s is 8
            //then backtrack
            isComplete(count); 
        }
    }
    
    //checks if backtrack should be done
    void isComplete(int zro_count)//checks for backtrack
    {
        if(zro_count==8)
        {
            System.out.println("No place found for Queen to be placed !!");
            System.out.println("Backtracking..!!");
            backtrack();
        }    
    }
    
    boolean solutionFound()
    {
        int count=0;
        for(int i=0;i<8;i++)
        {
            
            for(int j=0;j<8;j++)
            {
                if(grid[i][j]==1)
                {
                    count++;
                }
            }
        }   
        if(count==8)
        {
           // System.out.println(count);
            System.exit(0);
        }
        return false;
    }
    
    //backtrack function
    void backtrack()
    {
        int prev_x,prev_y;//topmost point in stack or pos of queen in prev row
        int new_y=99; 
        
        if(!stack.empty())//solves Empty stack exception
        {
           
            prev_y=stack.pop();//y or col_no is popped first because it is at the top
            list.remove(list.lastIndexOf(prev_y));
            prev_x=stack.pop();//x or row no is pooped second
            list.remove(list.lastIndexOf(prev_x));
            
            System.out.println("POPPED Queen at pos :" +prev_x+" "+prev_y);
            System.out.println("New List of Queens "+list);
           
            grid[prev_x][prev_y]=0; //removing the prev bad pos of queen
            int zero_count=0;
            
            //all col b4 the prev_y are already bad ...
            //so no use searching pos b4 it
            //start from the next col
            for(int i=(prev_y+1);i<8;i++)//starting from the next col
            {
                System.out.println("Checking now at pos :"+prev_x+" "+i);
                if(checkValidity(prev_x,i) && checkDiagonal2(prev_x, i) && visitedRowCol(prev_x, i))
                {
                    grid[prev_x][i]=1; //if valid pos
                  
                    list.add(prev_x); //row remains the same
                    list.add(i); //new col pos
                    stack.push(prev_x);
                    stack.push(i); //new col pos
                    System.out.println("Putting Queen at new pos ;"+prev_x+" "+i);
                    new_y=i;
                    display();
                    break;
                }
                else
                {
                    zero_count++;//counting 0s after the prev_y till end of size of grid i.e here 7
                }
            }
            //if all pos after prev_y are 0
            //then no new pos for queen is found
            //again go backtrack to another prev row
            if(zero_count==(7-prev_y))
            {
                grid[prev_x][prev_y]=0;//removing the prev bad pos of queen
                backtrack();
            }
            //else new pos is found for queen to place
            //continue to next row now
            else
            {
                runAgain((prev_x+1),new_y);//with next row now
            }
        }
    }
    
    void runAgain(int new_row,int new_col)
    {
        outer:for(int i=(new_row);i<8;i++)
        {
            int count=0;
            for(int j=0;j<8;j++)
            {
                if(checkValidity(i,j)== true && checkDiagonal2(i,j)==true && visitedRowCol(j, j)==true)
                {
                   grid[i][j]=1;
                   list.add(i);
                   list.add(j);
                   stack.push(i);
                   stack.push(j);
                  
                   System.out.println(i+"th ROW PASS Queens Placed At : "+stack);
                   
                   display();
                   
                   break;//goes to next row now
                }
                else
                {
                   grid[i][j]=0;
                   count++;//counting 0s
                }
                
            }
            if(solutionFound())
            {
               //check sol found
            }
              //if no place is found for queen to be placed in a row i.e num of 0s is 8
            //then backtrack
            isComplete(count);
    
        }
    }
    
    //this fn checks all positions at north south west east positions
    boolean checkValidity(int i,int j)
    {
        if(j==0)//checking boundary condtn for the 0th column
        {
            if((i+1<8 && j>=0) && (i-1>=0 && j+1<8))
            {
                if(grid[i-1][j]==1 || grid[i+1][j]==1 || grid[i][j]==1 || grid[i][j+1]==1)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        else if(j==7)
        {
             if((i+1<8 && j-1>=0) && (i-1>=0 && j<8))
            {
                if(grid[i-1][j]==1 || grid[i+1][j]==1 || grid[i][j-1]==1 || grid[i][j]==1)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        else if(i==7)
        {
            if((i<8 && j-1>=0) && (i-1>=0 && j+1<8))
            {
                if(grid[i-1][j]==1 || grid[i][j]==1 || grid[i][j-1]==1 || grid[i][j+1]==1)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        else
        {
             if((i+1<8 && j-1>=0) && (i-1>=0 && j+1<8))
            {
                if(grid[i-1][j]==1 || grid[i+1][j]==1 || grid[i][j-1]==1 || grid[i][j+1]==1)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
       
        return false;
    }
    
    //checks diagonal postions
    //all queens placed diagonally have
    //the difference between its x and y corod the same
    //like queens at 2,2 and 4,4
    //like queens at 1,0 and 2,1
    boolean checkDiagonal2(int m,int n)
    {
        int x=99,y=99;
       
     
            for(int k=0;k<list.size();k+=2)
            {
                x=list.get(k);
                y=list.get(k+1);
                
                int xdiff=Math.abs(m-x);
                int ydiff=Math.abs(n-y);

                if(xdiff==ydiff)
                {
                    return false;
                }
                
            }
      return true;
    }
  
    //marks the columns where queens is already placed
    boolean visitedRowCol(int m,int n)
    {
        int visited_col=99;
            for(int k=0;k<list.size();k+=2)
            {      
                visited_col=list.get(k+1);
                if(visited_col==n)
                {
                    return false;
                }        
            }
            
         return true;   
   
    }
   
    //display the grid
    void display()
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                System.out.print(grid[i][j]+" ");
            }
            System.out.println();
        }
    }
    
}

class Runner 
{
    public static void main(String args[])
	{
        //by kunal bhoge
            EightQueens game=new EightQueens();
            game.run();
	}
    
}
