1) Client tells server side your geo position, server response "ok" or "error" 	  

REQUEST:
	1. id=<user id>
	2. type=update_geo_position
	3. latitude=<X-axis>
	4. longitude=<Y-axis>
	Example:		
	domain/core?id=a16d723geqf&type=update_geo_position&latitude=59.856954&longitude=29.874587
RESPONSE:
	JSON encoded
	1. status: ok | error
	2. message: if need more details
	Example:
	{
		"status"  : "ok",
		"message" : ""
	}	
	{
		"status"  : "error",
		"message" : "overload db, sorry("
	}

2) Client requires load of buildings at polygon, server returns set of buildings descriptions 

REQUEST:
	1. id=<user id>
	2. type=get_buildings_at_polygon
	3. point1_latitude=<X-axis>
	4. point1_longitude=<Y-axis>
	5. point2_latitude=<X-axis>
	6. point2_longitude=<Y-axis>
	Example:		
	domain/core?id=a16d723geqf&type=get_buildings_at_polygon&point1_latitude=59.856954&point1_longitude=29.874587&point2_latitude=60.856912&Point2point2_longitude=30.874586

RESPONSE:
	1. id - uniq identificator of build
	2. current_load - сurrent building load
	3. max_load - maximum building load
	4. description of building by points
	Example
	JSON encoded
	[
		{
			"id" : "5341745e9153f6b9bb9c1d3b248810e8baf94e62",
			"current_load" : "30",
			"max_load" : 50,	
			"points":
			[
				["59.1214","29.21312"],
				["60.546412","30.2432"],
				["59.5461","29.98567"],
				["59.4564","29.5462"]
			]
		},		
		{
			"id" : "d027ae4771aa4d4de936aa4767a34f87e68a2139",
			"current_load" : "54",
			"max_load" : 150,			
			"points" : 
			[
				["51.1214","29.21312"],
				["60.546412","30.2432"],
				["50.5461","29.8567"],
				["61.5461","30.5567"],
				["62.5461","31.77567"],
				["54.4564","29.5462"]
			]
		}
	]
	
 

