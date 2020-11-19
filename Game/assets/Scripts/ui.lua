require("engine.gfx.ENGINE")

HP_HIGH_RED = 121
HP_HIGH_GREEN = 255
HP_HIGH_BLUE = 95

HP_MED_RED = 194
HP_MED_GREEN = 138
HP_MED_BLUE = 78

HP_LOW_RED = 255
HP_LOW_GREEN = 43
HP_LOW_BLUE = 43

function Update()
	MaxHPKey = Engine.storage.GetString("MaxHP");
	HPKey = Engine.storage.GetString("HP");
	HP = Engine.storage.GetInt(HPKey);
	MaxHP = Engine.storage.GetInt(MaxHPKey);
	hpMessage = "HP:"..HP.."/"..MaxHP;
	Engine.gfx.DrawString(0, 16, 16, 0, 0, 0,hpMessage);
	healthPercent = (HP / MaxHP);
	barWidth = (HP / MaxHP) * 200;
	if(healthPercent > 0.67) then
		Engine.gfx.DrawFilledRect(8 * (#hpMessage) + 2,20, barWidth, 16, HP_HIGH_RED, HP_HIGH_GREEN, HP_HIGH_BLUE);
	end

	if(healthPercent < 0.67 and healthPercent > 0.33) then
		Engine.gfx.DrawFilledRect(8 * (#hpMessage) + 2,20, barWidth, 16, HP_MED_RED, HP_MED_GREEN, HP_MED_BLUE);
	end

	if(healthPercent < 0.33) then
		Engine.gfx.DrawFilledRect(8 * (#hpMessage) + 2,20, barWidth, 16, HP_LOW_RED, HP_LOW_GREEN, HP_LOW_BLUE);
	end

	x = Engine.storage.GetInt("Player.x");
	y = Engine.storage.GetInt("Player.y");

	mx = Engine.input.GetMousePosX();
	my = Engine.input.GetMousePosY();

	Engine.gfx.DrawString(0,32,16,0,0,0,"Pos: " .. x .. ", " .. y);
	Engine.gfx.DrawString(0,48,16,0,0,0,"Mouse: " .. mx .. ", " .. my);
end