require("engine.gfx.ENGINE");

function Update()
	if(Engine.input.IsKeyDown("ESCAPE")) then
		Engine.core.Close();
	end
end