import java.util.*;
import java.io.*;

public class KillTheCovid {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        HashMap<String, String[]> map_neigh = new HashMap<String, String[]>();
        myDoubleHashMap map_pos = new myDoubleHashMap();

        System.out.println("Would you like to initialize the hexagons? (Y/ N)");
        String ans = sc.next().toUpperCase();
        if (ans.equals("Y")) {
            File file =  new File("initialization.txt");
            try {
                Scanner initialization_file = new Scanner(file);
                while (initialization_file.hasNextLine()) {
                    String data = initialization_file.nextLine();
                    String[] instruction_data = data.split(" ");
                    String new_hex = instruction_data[1];
                    String neighbouring_hex = instruction_data[2];
                    int border = Integer.parseInt(instruction_data[3]);
                    insert(new_hex, neighbouring_hex, border, map_neigh, map_pos);
                    System.out.print(new_hex + " has been inserted, here are the neighbours: ");
                    String[] neighs = map_neigh.get(new_hex);
                    for (int i = 0; i < 6; i++) {
                        if (neighs[i] != null) {
                            System.out.print("[" + i + ", " + neighs[i] + "] ");
                        }
                    }
                    System.out.println();
                }
                System.out.println("Initialization Complete");
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        String instruction = sc.next().toLowerCase();

        while (!instruction.equals("quit")) {
            if (instruction.equals("query")) {
                String hex = sc.next();
                if (map_neigh.isEmpty()) {
                    System.out.println("Invalid query, please initialize you hexagons by typing `insert initial_hex x 0`");
                    instruction = sc.next().toLowerCase();
                    continue;
                } else if (map_neigh.get(hex) == null || map_neigh.get(hex).length == 0 ) {
                    System.out.println("Invalid query, " + hex + " does not exist or it has been removed");
                    instruction = sc.next().toLowerCase();
                    continue;
                }
                hex = hex.toUpperCase();
                String[] neighs = map_neigh.get(hex);
                System.out.print("Here are the neighbours for " + hex + ": ");
                for (int i = 0; i < 6; i++) {
                    if (neighs[i] != null) {
                        System.out.print("[" + i + ", " + neighs[i] + "] ");
                    }
                }
                System.out.println();
            } else if (instruction.equals("insert")) {
                String new_hex = sc.next().toUpperCase();
                String neighbouring_hex = sc.next().toUpperCase();
                int border = sc.nextInt();
                insert(new_hex, neighbouring_hex, border, map_neigh, map_pos);
                System.out.print("Inserted! Here are the neighbours for " + new_hex + ": ");
                String[] neighs = map_neigh.get(new_hex);
                for (int i = 0; i < 6; i++) {
                    if (neighs[i] != null) {
                        System.out.print("[" + i + ", " + neighs[i] + "] ");
                    }
                }
                System.out.println();
            } else if (instruction.equals("remove")) {
                String hex = sc.next().toUpperCase();
                remove(hex, map_neigh, map_pos);
                // System.out.println("Removed!");
            } else {
                System.out.println("Invalid instructions, program will be terminating...");
                break;
            }
            instruction = sc.next().toLowerCase();
        }
    }

    static int neighbourIndex (int ind) {
        if (ind <= 2) {
            return ind + 3;
        } else {
            return ind - 3;
        }
    }

    static void insert (String new_hex, String neighbouring_hex, int border, HashMap<String, String[]> arr_neigh, myDoubleHashMap arr_dist) {

        if (arr_neigh.isEmpty()) {
            pos position = new pos(0, 0);
            String[] x = new String[6];
            arr_neigh.put(new_hex, x);
            arr_dist.put(new_hex, position);
            return;
        }

        arr_neigh.get(neighbouring_hex)[border] = new_hex;
        pos position = arr_dist.getValueOfKey(neighbouring_hex);
        pos new_position = new pos(0, 0);
        if (border == 0) {
            new_position.x = position.x;
            new_position.y = position.y + 866 * 2;
        } else if (border == 1) {
            new_position.x = position.x + 1500;
            new_position.y = position.y + 866;
        } else if (border == 2) {
            new_position.x = position.x + 1500;
            new_position.y = position.y - 866;
        } else if (border == 3) {
            new_position.x = position.x;
            new_position.y = position.y - 866 * 2;
        } else if (border == 4) {
            new_position.x = position.x - 1500;
            new_position.y = position.y - 866;
        } else if (border == 5) {
            new_position.x = position.x - 1500;
            new_position.y = position.y + 866;
        }

        arr_dist.put(new_hex, new_position);

        String[] new_neighbours = new String[6];
        
        // Update the neighbours of new_hex as well as the neighbours' neighbor for new_hex
        pos loc_0 = new pos(new_position.x, new_position.y + 866 * 2);
        pos loc_1 = new pos(new_position.x + 1500, new_position.y + 866);
        pos loc_2 = new pos(new_position.x + 1500, new_position.y - 866);
        pos loc_3 = new pos(new_position.x, new_position.y - 866 * 2);
        pos loc_4 = new pos(new_position.x - 1500, new_position.y - 866);
        pos loc_5 = new pos(new_position.x - 1500, new_position.y + 866);
        if (arr_dist.getKeyOfValue(loc_0) != null) {
            String n = arr_dist.getKeyOfValue(loc_0);
            new_neighbours[0] = n;
            arr_neigh.get(n)[neighbourIndex(0)] = new_hex;        
            // System.out.println("Neighbours 0 updated");
        }
        if (arr_dist.getKeyOfValue(loc_1) != null) {
            String n = arr_dist.getKeyOfValue(loc_1);
            new_neighbours[1] = n;
            arr_neigh.get(n)[neighbourIndex(1)] = new_hex;        
            // System.out.println("Neighbours 1 updated");
        }
        if (arr_dist.getKeyOfValue(loc_2) != null) {
            String n = arr_dist.getKeyOfValue(loc_2);
            new_neighbours[2] = n;
            arr_neigh.get(n)[neighbourIndex(2)] = new_hex;        
            // System.out.println("Neighbours 2 updated");
        }
        if (arr_dist.getKeyOfValue(loc_3) != null) {
            String n = arr_dist.getKeyOfValue(loc_3);
            new_neighbours[3] = n;
            arr_neigh.get(n)[neighbourIndex(3)] = new_hex;        
            // System.out.println("Neighbours 3 updated");
        }
        if (arr_dist.getKeyOfValue(loc_4) != null) {
            String n = arr_dist.getKeyOfValue(loc_4);
            new_neighbours[4] = n;
            String [] test = arr_neigh.get(n);
            test[neighbourIndex(4)] = new_hex;        
            // System.out.println("Neighbours 4 updated");
        }
        if (arr_dist.getKeyOfValue(loc_5) != null) {
            String n = arr_dist.getKeyOfValue(loc_5);
            new_neighbours[5] = n;
            arr_neigh.get(n)[neighbourIndex(5)] = new_hex;        
            // System.out.println("Neighbours 5 updated");
        }

        arr_neigh.put(new_hex, new_neighbours);

    }

    static void remove (String hex, HashMap<String, String[]> arr_neigh, myDoubleHashMap arr_dist) {
        
        String[] neighbours = arr_neigh.get(hex);
        int count = 0;
        for (int i = 0; i < 6; i++) {
            if (neighbours[i] != null) {
                count++;
            }
        }
        
        boolean check = false;

        for (int i = 0; i < 6; i++) {
            if (count == 1) {
                break;
            } else if (neighbours[i] != null) {
                check = neighbours[(i+1)%6] == null 
                    && neighbours[(i+5)%6] == null 
                    && (neighbours[(i+2)%6] == null ||
                    neighbours[(i+3)%6] == null ||
                    neighbours[(i+4)%6] == null); 
                if (check) {
                    break;
                }
            }
        }

        arr_neigh.remove(hex);
        arr_dist.removeFromKey(hex);

        // removing hex from each of his neighbours
        String curr_ind;
        for (int i = 0; i < 6; i++) {
            curr_ind = neighbours[i];
            if (curr_ind != null) {
                arr_neigh.get(curr_ind)[neighbourIndex(i)] = null;
            }
        }
        
        if (check && !checkByDFS(arr_neigh)) {
            System.out.println("Sorry this neighbour cannot be removed as it will result in 2 disconnected arrays of hexagons");
            for (int i = 0; i < 6; i++) {
                if (neighbours[i] != null) {
                    insert(hex, neighbours[i], neighbourIndex(i), arr_neigh, arr_dist);
                    return;
                }
            }
        }

        System.out.print(hex + " has been removed from: ");
        for (int i = 0; i < 6; i++) {
            curr_ind = neighbours[i];
            if (curr_ind != null) {
                System.out.print("[" + curr_ind + ", " + neighbourIndex(i) + "] ");
            }
        }

        System.out.println();
    }

    static boolean checkByDFS (HashMap<String, String[]> arr_neigh) {
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        Set keys = arr_neigh.keySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            visited.put((String)iterator.next(), false);
        }

        Iterator i = keys.iterator();
        Queue<String> q = new LinkedList<String>();
        q.offer((String)i.next());
        int count = 0;
        while (!q.isEmpty()) {
            String hex = q.poll();
            if (!visited.get(hex)) {
                count++;
                visited.replace(hex, true);
                String[] neighbours = arr_neigh.get(hex);
                for (int j = 0; j < 6; j++) {
                    if (neighbours[j] != null) {
                        q.offer(neighbours[j]);
                    }
                }
            }
        }

        return count == arr_neigh.size();
    }
}



//In each of the smaller equilateral triangle, take the length of the sides as 1000 and the height of triangle as (sqrt(3)/2*10)*100 = 866
class pos {
    long x;
    long y;

    public pos(long x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        // int prime1 = 9677;
        // int prime2 = 9679;
        // return (int) this.x * prime1 + (int) this.y * prime2;
        return (Integer.toString((int)x) + "," + Integer.toString((int)y)).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        pos other = (pos) obj;;
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.x == other.x && this.y == other.y) {
            return true;
        } else {
            return false;
        }
    }
}

class myDoubleHashMap {
    private HashMap<String, pos> keyMap;
    private HashMap<pos, String> valMap;
  
    public myDoubleHashMap() {
      keyMap = new HashMap<String, pos>();
      valMap = new HashMap<pos, String>();
    }

    public void put(String key, pos val) {
      keyMap.put(key,val);
      valMap.put(val,key);
    }

    public pos getValueOfKey(String key) {
      return keyMap.get(key);
    }

    public String getKeyOfValue(pos val) {
      return valMap.get(val);
    }

    public void removeFromKey(String key) {
        pos val = keyMap.get(key);
        keyMap.remove(key);
        valMap.remove(val);
    }

    public void removeFromValue(pos position) {
        String key = valMap.get(position);
        valMap.remove(position);
        keyMap.remove(key);
    }
}