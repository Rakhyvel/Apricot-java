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
world.addTickable(t);
world.addRenderable(r);
world.addInputListener(i);
```
I recommend you put those statements in an object's constructor to keep things organized, and replace the relevant t, r, or i with `this`.

### Implementing Tickable
Now, lets create a simple object for our application. Our object will be a _**Tickable**_ object, meaning it does something every 1/60 seconds, a _**Renderable**_ object, meaning it will draw something to the screen, and an _**InputListener**_, meaning it will do something when given input by the user. These are all interfaces in Apricot, and we create a class to implement these like so:
```
public class DemoObject implements Tickable {

  public DemoObject() {
    // Replace world with a reference to your world
    world.addTickable(this);
  }
  
  @Override
  public void tick() {
    // Add code here, and it will be executed 60 times a second
  }
  
  @Override
  public void remove() {
    // I recomend that you do this, of course replace world with a reference to your world
    world.removeTickable(this);
    
    // You may of course just return null, but that is unreliable
  }
  
}
```
Again, I recommend doing `world.addTickable(this);` in the constructor rather than `world.addTickable(new DemoObject());`, especially if your objects implement _**Renderable**_ or _**InputListener**_ as well.

### Implementing Renderable
Renderable objects are able to draw to the screen. The frame rate is chaotic, and you should not rely on it for animations. Below is DemoObject if it implemented _**Renderable**_:
```
public class DemoObject implements Renderable {
  Tuple position;

  public DemoObject() {
    // Replace world with a reference to your world
    world.addRenderable(this);
    position = new Tuple(300, 200);
  }
  
  @Override
  public void render(Render r) {
    // Add code here, and it will be executed 60 times a second
  }
  
  @Override
  public int getRenderOrder() {
    return 0;
  }
  
  @Override
  public Tuple getPosition() {
    return position;
  }
  
  @Override
  public void setPosition(Tuple position){
    this.position = position;
  }
  
  @Override
  public void remove() {
    world.removeRenderable(this);
  }
  
}
```
A couple things to note with this one. First, is that all _**Renderable**_ objects must have a Tuple representing their position. Tuples can be thought of as either 2D points, vectors, or dimensions, and here we're using them as a point for our object.

Another thing is the render order. Render order determines when the object is drawn to the screen. The lower the number you return, the earlier the object is drawn. If you have a background object and a foreground object, you would want the background object to return a lower integer than the foreground object in order for the foreground object to be drawn to the screen last.

#### Methods in Render
The Render object passed by Apricot to our objects Render method can be used like Java AWT's Graphics object. There are a couple of cool and optimized methods we can use to draw things to our screen. The first is:
```
@Override
public void Render(Render r){
  int x = position.getX();                // The X coordinate of the top-left corner of our rectangle
  int y = position.getY();                // The Y coordiante of the top-left corner of our rectangle
  int width = 50;                         // The width of our rectangle
  int height = 50;                        // The height of our rectangle
  int color = r.argb(255, 255, 128, 0);   // The color of our rectangle
  r.drawRect(x, y, width, height, color);
}
```
Apricot works in a similar way to Java AWT Graphics in that the origin of the window is in the top-left. Also, be aware that Apricots drawRect() method fills the rectangle in with the color, and is equivilent to Java AWT Graphics' fillRect() method. There is no Java AWT Graphics drawRect equivalent in Apricot.

The argb() method in Apricot transforms the alpha channel, Red channel, green channel, and blue channel to a 32 bit integer. In essence, all it does is the following:
```
int color = 255 << 24 | 255 << 16 | 128 << 8 | 0;
```
This is lengthy to write, and not very readable, which is why I recommend using the argb() method. But it is good to know both ways if you want to write your own image manipulation methods.

Here is how to draw an image to the screen using Apricot:
```
@Override
public void Render(Render r){
  int x = position.getX();                                            // The X coordinate of the center of our image
  int y = position.getY();                                            // The Y coordiante of the center of our image
  int width = 50;                                                     // The width of our image
  int[] image = Render.image.loadImage("... path to your image ..."); // Image
  float opacity = 1f;                                                 // The color of our rectangle
  float roation = 0f;                                                 // Rotation of our image in radians
  r.drawImage(x, y, width, image, opacity, rotation);
}
```
Images in Apricot are represented by an integer array, representing the 32bit color pixels. 

### Implementing InputListener
_**InputListener**_ objects have their input() method called whenever:
- A key is pressed
- A key is released
- Mouse button (any) is pressed
- Mouse button (any) is released
- Mouse is moved
- Mouse is dragged
- Mousewheel is moved

Here is an example class implementing InputListener:
```
public class DemoObject implements InputListener {

  public DemoObject() {
    // Replace world with a reference to your world
    world.addInputListener(this);
  }
  
  @Override
  public void input() {
    // Add input code here
  }
  
  @Override
  public void remove() {
    // I recomend that you do this, of course replace world with a reference to your world
    world.removeInputListener(this);
  }
  
}
```

#### Keyboard input
You can check to see if a keyboard key is down by calling `apricotObject.getKeyboard().keyDown(int keyEventKeyCode);`, and giving it the KeyEvent keycode.

### MouseInput
You can get the Mouse's coordinates relative to the Canvas by doing `apricotObject.getMouse().getX();` or `apricotObject.getMouse().getY();`.

You can check whether the left or right mouse button are down by doing `apricotObject.getMouse().isLeftDown();` or `apricotObject.getMouse().isRightDown();`.

You can get the mousewheels position by doing `apricotObject.getMouse().getWheelPosition;`.
