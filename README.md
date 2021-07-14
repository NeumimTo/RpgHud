Client mod that improves features. 

=== Install Instructions ===

=== How it works ===

Server can send a packet to client, that tells the client to change vanilla entity model to bedrock model providede by resourcepack.  


Differences between ModelEngine:

- NtHud needs to be installed on the client
- NtHud does not need to perform costly mathematical computations on the server to simulate joint movement
- NtHud is able to achieve much smoother animation
- ModelEngine is not free nor opensource

=== Protocol ===

Communication between server and client is done via plugin channel `nth:gui`

When player joins the server hello packet is sent. Hello packet contains protocol version.

Current protocol version is: `nth|1`

|Description|Packet|
|-----------|------|
|Starts countdown on the client (todo) | `cd;<long cd>;<path to icon in resourcepack>` |
|Sets model for one specific entity |`sm;<entityuuid>;<textureLocation>;<animationFileLocation>;<modelLocation>` |
|Plays animation for one specific entity | `pa;<entityuuid>;<animationname>` |
