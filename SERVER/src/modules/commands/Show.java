package modules.commands;

import modules.Command;
import network.Response;
import java.io.*;

/**
 * Класс Show реализует команду вывода всех элементов коллекции в строковом представлении.
 */
public class Show implements Command {
    public static String inputFileName;
    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "show";
    }
    public static String line = "aaaaaaaaaaaaaaaaaaaaaaaaa";
    @Override
    public Response call(String strArg, Serializable objectArg) {
        try {
            if (inputFileName.isEmpty() || inputFileName.equals("null")) {
                return new Response("Коллекция пуста.");
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
                while ((line = reader.readLine()) != null) {
                    line = line + "\n";
                }
            }
        }
        catch(FileNotFoundException a){
            System.out.println("Коллекция пуста или же отсутствует");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return new Response(line);
    }


}

