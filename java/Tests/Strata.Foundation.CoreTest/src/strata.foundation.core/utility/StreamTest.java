//////////////////////////////////////////////////////////////////////////////
// StreamTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import org.junit.Test;

public
class StreamTest
{
    @Test
    public void
    getTopTenA()
        throws Exception
    {
/*
        String[] topTen =
            new Scanner(new File("C://tmp/hamlet.txt"))
                .useDelimiter("[^a-zA-Z]+")
                .tokens()
                .collect(
                    Collectors.groupingBy(
                        String::toLowerCase,
                        Collectors.summingInt(s -> 1)))
                .entrySet()
                .stream()
                .sorted((x,y) -> y.getValue() - x.getValue())
                .limit(10)
                .map( w -> w.getKey())
                .toArray(String[]::new);

        assertArrayEquals(
            new String[] {"the","and","to","of","i","you","a","my","hamlet","in"},
            topTen);
    }

    @Test
    public void
    getTopTenB()
        throws Exception
    {
        String[] topTen =
            Files
                .lines(Paths.get("C://tmp/hamlet.txt"))
                .map( line -> line.split("[^a-zA-Z]+"))
                .flatMap(Arrays::stream)
                .filter( w -> !w.isEmpty() )
                .collect(
                    Collectors.groupingBy(
                        String::toLowerCase,
                        Collectors.summingInt(s -> 1)))
                .entrySet()
                .stream()
                .sorted((x,y) -> y.getValue() - x.getValue())
                .limit(10)
                .map( w -> w.getKey())
                .toArray(String[]::new);

        assertArrayEquals(
            new String[] {"the","and","to","of","i","you","a","my","hamlet","in"},
            topTen);
    }

    @Test
    public void
    getTopTenZhengyan()
        throws Exception
    {
        File file = new File("C://tmp/hamlet.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        Map<String,Integer> map = new HashMap<String,Integer>();
        String line;
        while ((line = bufferedReader.readLine()) != null)
        {
            line = line.toLowerCase().replaceAll("[^a-z]"," ");
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens())
            {
                String token = tokenizer.nextToken();
                map.put(token,map.getOrDefault(token,0) + 1);
            }
        }

        String[] result = new String[10];
        List<Map.Entry<String,Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(
            new Comparator<Integer>()
            {
                @Override
                public int compare(Integer o1,Integer o2)
                {
                    return o2 - o1;
                }
            }));
        for (int i = 0;i < 10;i++)
        {
            result[i] = list.get(i).getKey();

        }

        assertArrayEquals(
            new String[] {"the","and","to","of","i","you","a","my","hamlet","in"},
            result);
*/
    }
}

//////////////////////////////////////////////////////////////////////////////
