# Apricot
Apricot is a library used to make Java applications quick and easy. Why re-invent the wheel every time you start a new project? Apricot gives you access to:
- Easy window and JFrame management
- Easy application stage partitioning with Worlds
- A fast, optimized game loop
- Fast rendering engine, perlin noise generation, and image manipulation

## Tutorial
### Setting up Apricot object
After including the JAR in your project, you will want to create an Apricot object like so:
``` 
String title = "This is my Java application!";
int width = 600;
int height = 400;
Apricot apricotObject = new Apricot(title, width, height);
```

### Setting up Worlds
After making your Apricot object, you will want to create some World objects to hold the objects in your application, and set one to be the default world on startup, like so:
```
World world = new World();
```
Finally, to tell your Apricot object which World to use by default, do `apricotObject.setWorld(world);`. This can be done even if the World doesn't contain anything. In fact, you can even do `apricotObject.setWorld(null);`, and you will be fine. 

To find out which World your Apricot object is working with, you can do `apricotObject.getWorld();`, which will return a reference to the working World.

To add a _**Tickable**_, _**Renderable**_, or _**InputListener**_ object to a World, you use:
```
world.add(object);
```
I recommend you put those statements in an object's constructor to keep things organized, and replace the relevant `object` with `this`.

### Implementing Tickable
Tickable objects have a method that is called regularly. The default time is 60 ticks per second, though you can change this with the `setDeltaT()` from your Apricot object. `dt` is the time between ticks, in milleseconds. Below is a sample class.
```
public class DemoObject implements Tickable {

  public DemoObject() {
    // Replace world with a reference to your world
    world.add(this);
  }
  
  @Override
  public void tick() {
    // Add code here, and it will be executed 60 times a second
  }
  
  @Override
  public void remove() {
    // I recomend that you do this, of course replace world with a reference to your world
    world.remove(this);
  }
  
}
```

### Implementing Renderable
Renderable objects are given access to a Graphics2D instance to draw with every frame. Use this to draw sprites and whatever to the screen using Java's built in Graphics2D class.
```
public class DemoObject implements Renderable {

  public DemoObject() {
    // Replace world with a reference to your world
    world.add(this);
  }
  
  @Override
  public void render(Graphics2D g) {
    // Add code here, and you can draw something every frame
  }
  
  @Override
  public int getRenderOrder() {
    // Think of this like layers in photoshop or gimp
    return 0;
  }
  
  @Override
  public void remove() {
    world.remove(this);
  }
  
}
```

### Implementing InputListener
_**InputListener**_ objects have their `input()` method called whenever an input event happens, and are passed in the `InputEvent` enum corresponding to whichever event it was.
```
public class DemoObject implements InputListener {

  public DemoObject() {
    // Replace world with a reference to your world
    world.add(this);
  }
  
  @Override
  public void input(InputEvent e) {
    // Add input code here
  }
  
  @Override
  public void remove() {
    // I recomend that you do this, of course replace world with a reference to your world
    world.remove(this);
  }
  
}
```
Here are all InputEvents
| *InputEvent*           | *Description*                                                  |
|------------------------|----------------------------------------------------------------|
| `KEY_PRESSED`          | A keyboard key has been pressed                                |
| `KEY_RELEASED`         | A keyboard key has been released                               |
| `MOUSE_LEFT_DOWN`      | Left mouse button was pressed                                  |
| `MOUSE_RIGHT_DOWN`     | Right mouse button was pressed                                 |
| `MOUSE_LEFT_RELEASED`  | Left mouse button was released                                 |
| `MOUSE_RIGHT_RELEASED` | Right mouse button was released                                |
| `MOUSE_MOVED`          | Mouse moved                                                    |
| `MOUSE_DRAGGED`        | Mouse dragged                                                  |
| `MOUSE_WHEEL_MOVED`    | Mouse-wheel moved up or down (Use apricot.mouse.mouseWheelPos) |
| `WORLD_CHANGE`         | Apricot's world has been changed                               |

#### Keyboard input
You can check to see if a keyboard key is down by calling `apricotObject.getKeyboard().keyDown(int keyEventKeyCode);`, and giving it the KeyEvent keycode.

### MouseInput
You can get the Mouse's coordinates relative to the Canvas by doing `apricotObject.getMouse().getX();` or `apricotObject.getMouse().getY();`.

You can check whether the left or right mouse button are down by doing `apricotObject.getMouse().isLeftDown();` or `apricotObject.getMouse().isRightDown();`.

You can get the mousewheels position by doing `apricotObject.getMouse().getWheelPosition;`.
