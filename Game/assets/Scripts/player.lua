require("engine.gfx.ENGINE")

x = 0;
y = 0;

input = Engine.input;
gfx = Engine.gfx;
storage = Engine.storage;
DeltaTime = Engine.DeltaTime;

function Init()
	x = Engine.storage.GetInt("Player.x");
	y = Engine.storage.GetInt("Player.y");
	Engine.storage.SetInt("Player.stats.MaxHP", 1000);
	Engine.gfx.LoadImage("assets/player.png", "Player");
end

function Update()

	if input.IsKeyDown("A")
	then
		x = x - 1;
	end
	
	if input.IsKeyDown("D")
	then
		x = x + 1;
	end
	
	if input.IsKeyDown("W")
	then
		y = y - 1;
	end
	
	if input.IsKeyDown("S")
	then
		y = y + 1;
	end
	
	gfx.DrawImage("Player",x,y,32,32);
	storage.SetInt("Player.x",x);
	storage.SetInt("Player.y",y);

	MaxHP = storage.GetInt("Player.stats.MaxHP");

	if(input.IsKeyDown("E")) then
		HP = Engine.storage.GetInt("Player.stats.HP");
		HP = HP + (60 * Engine.DeltaTime());
		if(HP > MaxHP) then HP = MaxHP; end
		storage.SetInt("Player.stats.HP", HP);
	end

	if(input.IsKeyDown("Q")) then
		HP = Engine.storage.GetInt("Player.stats.HP");
		HP = HP - 1;
		if(HP < 0) then HP = 0; end
		storage.SetInt("Player.stats.HP", HP);
	end

end

function Close()
	
end