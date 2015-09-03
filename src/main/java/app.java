import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    /* Index */
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Book> books = Book.all();
      model.put("books", books);
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      String bookIsbn = request.queryParams("bookIsbn");
      Book newBook = new Book(title,bookIsbn);
      newBook.save();
      response.redirect("/books");
      return null;
    });
    //
    // /* Book list/form --> See a particular category */
    // get("/books/:id", (request,response) ->{
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int id = Integer.parseInt(request.params(":id"));
    //   Book category = Book.find(id);
    //   model.put("category", category);
    //   model.put("allTasks", Task.all());
    //   model.put("template", "templates/category.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // /* Task list/form --> POST a new task */
    // post("/tasks", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   String description = request.queryParams("description");
    //   String due_date = request.queryParams("due_date");
    //   boolean is_completed =  Boolean.parseBoolean(request.queryParams("is_completed"));
    //   Task newTask = new Task(description, due_date, is_completed);
    //   newTask.save();
    //   response.redirect("/tasks");
    //   return null;
    // });
    //
    // /* Index --> Tasks*/
    // get("/tasks", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   List<Task> tasks = Task.all();
    //   model.put("tasks", tasks);
    //   model.put("template", "templates/tasks.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // /* Book page --> POST a task to this category */
    // post("/add_tasks", (request, response) -> {
    //   int taskId = Integer.parseInt(request.queryParams("task_id"));
    //   int categoryId = Integer.parseInt(request.queryParams("category_id"));
    //   Book category = Book.find(categoryId);
    //   Task task = Task.find(taskId);
    //   category.addTask(task);
    //   response.redirect("/books/" + categoryId);
    //   return null;
    // });
    //
    // get("/tasks/:id", (request,response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int id = Integer.parseInt(request.params(":id"));
    //   Task task = Task.find(id);
    //   model.put("task", task);
    //   model.put("allCategories", Book.all());
    //   model.put("template", "templates/task.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    //
    //
    // /* Task page --> POST a category to this task */
    // post("/add_books", (request, response) -> {
    //   int taskId = Integer.parseInt(request.queryParams("task_id"));
    //   int categoryId = Integer.parseInt(request.queryParams("category_id"));
    //   Book category = Book.find(categoryId);
    //   Task task = Task.find(taskId);
    //   task.addBook(category);
    //   response.redirect("/tasks/" + taskId);
    //   return null;
    // });
    //
    // get("/tasks/:id/edit", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Task task = Task.find(Integer.parseInt(request.params("id")));
    //   //String new_task_name = request.queryParams("new_task_name");
    //   //task.update("new_task_name");
    //   model.put("task", task);
    //   model.put("template", "templates/tasks-edit-form.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/tasks/:id/edit", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Task task = Task.find(Integer.parseInt(request.params(":id")));
    //   String new_task_name = request.queryParams("new_task_name");
    //   boolean is_completed =  Boolean.parseBoolean(request.queryParams("is_completed"));
    //   task.update(new_task_name);
    //   task.updateIsCompleted(is_completed);
    //
    //   model.put("tasks",Task.all());
    //   model.put("task", task);
    //   model.put("template", "templates/tasks.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/tasks/:id/delete", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Task task = Task.find(Integer.parseInt(request.params("id")));
    //   task.delete();
    //   response.redirect("/tasks");
    //   return null;
    //
    // });
    //
  }
}
