/*
To get new mapdata entry the query below on https://overpass-turbo.eu/
*/
[out:json];
// gather results
//filter country
{{geocodeArea:luxembourg}}->.searchArea;
(
  // query part for: highway=motorway
  node["highway"="motorway"](area.searchArea);>;
  way["highway"="motorway"](area.searchArea);>;
  relation["highway"="motorway"](area.searchArea);>;

  // query part for: highway=motorway_link
  node["highway"="motorway_link"](area.searchArea);>;
  way["highway"="motorway_link"](area.searchArea);>;
  relation["highway"="motorway_link"](area.searchArea);>;

  // query part for: highway=motorway_junction
  node["highway"="motorway_junction"](area.searchArea);>;
  way["highway"="motorway_junction"](area.searchArea);>;
  relation["highway"="motorway_junction"](area.searchArea);>;

);
//print result
out;