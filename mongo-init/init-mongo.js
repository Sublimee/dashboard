db = db.getSiblingDB('mydatabase');
db.createCollection('cities');

db.cities.insertMany([
    { name: "Moscow" },
    { name: "Saint-Petersburg" }
]);