package agh.ics.oop;

import javafx.scene.image.Image;

public class Utils {
    public static int windowWidth;
    public static int windowHeight;
    public static Image[] animalImages;
    public static Image grassImage = new Image("grass_proj.jpg");
    public Utils(){
        for (int i = 0; i < 8; i++) {
            animalImages[i] = new Image(i +".jpg");
        }
    }
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static String getRandomAnimalName() {
        return allAnimals[getRandomNumber(0, allAnimals.length)];
    }


    private static String[] allAnimals = {"Aardvark",
            "Abyssinian",
            "Addax",
            "Adelie Penguin",
            "Affenpinscher",
            "Afghan Hound",
            "African Bullfrog",
            "African Bush Elephant",
            "African Civet",
            "African Clawed Frog",
            "African Forest Elephant",
            "African Grey Parrot",
            "African Palm Civet",
            "African Penguin",
            "African Tree Toad",
            "African Wild Dog",
            "Aidi",
            "Ainu",
            "Airedale Terrier",
            "Airedoodle",
            "Akbash",
            "Akita",
            "Akita Shepherd",
            "Alabai",
            "Alaskan Husky",
            "Alaskan Klee Kai",
            "Alaskan Malamute",
            "Alaskan Shepherd",
            "Albacore Tuna",
            "Albatross",
            "Aldabra Giant Tortoise",
            "Alligator",
            "Alligator Gar",
            "Alpaca",
            "Alpine Dachsbracke",
            "Alpine Goat",
            "Alusky",
            "Amazon Parrot",
            "Amazon River Dolphin (Pink Dolphin)",
            "American Alsatian",
            "American Bulldog",
            "American Cocker Spaniel",
            "American Cockroach",
            "American Coonhound",
            "American Eskimo Dog",
            "American Foxhound",
            "American Hairless Terrier",
            "American Leopard Hound",
            "American Pit Bull Terrier",
            "American Pygmy Goat",
            "American Robin",
            "American Staffordshire Terrier",
            "American Toad",
            "American Water Spaniel",
            "Amur Leopard",
            "Anatolian Shepherd Dog",
            "Anchovies",
            "Angelfish",
            "Anglerfish",
            "Angora Goat",
            "Ant",
            "Antarctic scale worm",
            "Anteater",
            "Antelope",
            "Appenzeller Dog",
            "Apple Head Chihuahua",
            "Arapaima",
            "Arctic Fox",
            "Arctic Hare",
            "Arctic Wolf",
            "Arizona Bark Scorpion",
            "Armadillo",
            "Armyworm",
            "Asian Elephant",
            "Asian Giant Hornet",
            "Asian Palm Civet",
            "Asiatic Black Bear",
            "Aurochs",
            "Aussiedoodle",
            "Aussiedor",
            "Australian Bulldog",
            "Australian Cattle Dog",
            "Australian Gecko",
            "Australian Kelpie Dog",
            "Australian Labradoodle",
            "Australian Mist",
            "Australian Retriever",
            "Australian Shepherd",
            "Australian Terrier",
            "Avocet",
            "Axolotl",
            "Aye Aye",
            "Animals that start with B",
            "Baboon",
            "Bactrian Camel",
            "Badger",
            "Baiji",
            "Bald Eagle",
            "Baleen Whale",
            "Balinese",
            "Balkan Lynx",
            "Banana Spider",
            "Banded Palm Civet",
            "Bandicoot",
            "Banjo Catfish",
            "Barb",
            "Barbet",
            "Barn Owl",
            "Barn Swallow",
            "Barnacle",
            "Barracuda",
            "Barramundi Fish",
            "Barred Owl",
            "Basenji Dog",
            "Basking Shark",
            "Bassador",
            "Basset Fauve de Bretagne",
            "Basset Hound",
            "Bassetoodle",
            "Bat",
            "Bavarian Mountain Hound",
            "Bea-Tzu",
            "Beabull",
            "Beagador",
            "Beagle",
            "Beagle Shepherd",
            "Beaglier",
            "Beago",
            "Bear",
            "Bearded Collie",
            "Bearded Dragon",
            "Beaski",
            "Beauceron",
            "Beaver",
            "Bedlington Terrier",
            "Beefalo",
            "Beetle",
            "Belgian Shepherd",
            "Belgian Tervuren",
            "Beluga Sturgeon",
            "Bengal Tiger",
            "Bergamasco",
            "Berger Picard",
            "Bernedoodle",
            "Bernese Mountain Dog",
            "Bernese Shepherd",
            "Bichir",
            "Bichon Frise",
            "Bichpoo",
            "Biewer Terrier",
            "Bighorn Sheep",
            "Bilby",
            "Binturong",
            "Bird",
            "Bird Of Paradise",
            "Birman",
            "Bison",
            "Black And Tan Coonhound",
            "Black Marlin",
            "Black Rhinoceros",
            "Black Russian Terrier",
            "Black Wasp",
            "Black Widow Spider",
            "Black-Footed Ferret",
            "Blister Beetle",
            "Blobfish",
            "Bloodhound",
            "Blue grosbeak",
            "Blue Iguana",
            "Blue Jay",
            "Blue Lacy Dog",
            "Blue Picardy Spaniel",
            "Blue Shark",
            "Blue Whale",
            "Blue-Ringed Octopus",
            "Bluefin Tuna",
            "Bluetick Coonhound",
            "Bobcat",
            "Bobolink",
            "Boggle",
            "Boglen Terrier",
            "Bolognese Dog",
            "Bombay",
            "Bongo",
            "Bonito Fish",
            "Bonnethead Shark",
            "Bonobo",
            "Booby",
            "Borador",
            "Border Collie",
            "Border Terrier",
            "Bordoodle",
            "Borkie",
            "Bornean Orang-utan",
            "Borneo Elephant",
            "Boston Terrier",
            "Bottlenose Dolphin",
            "Bouvier Des Flandres",
            "Bowfin",
            "Bowhead Whale",
            "Box Turtle",
            "Boxador",
            "Boxer Dog",
            "Boxerdoodle",
            "Boxsky",
            "Boxweiler",
            "Boykin Spaniel",
            "Brazilian Terrier",
            "British Timber",
            "Brittany",
            "Brookesia Micra",
            "Brown Bear",
            "Brown Hyena",
            "Brug",
            "Brussels Griffon",
            "Budgerigar",
            "Buffalo",
            "Bull Shark",
            "Bull Terrier",
            "Bulldog",
            "Bullfrog",
            "Bullmastiff",
            "Bumblebee",
            "Burmese",
            "Burmese Python",
            "Burrowing Frog",
            "Burrowing Owl",
            "Bush Baby",
            "Butterfly",
            "Butterfly Fish",
            "Cactus Mouse",
            "Caecilian",
            "Caiman",
            "Caiman Lizard",
            "Cairn Terrier",
            "Camel",
            "Camel Cricket",
            "Camel Spider",
            "Canaan Dog",
            "Canada Lynx",
            "Canadian Eskimo Dog",
            "Canadian Horse",
            "Cane Corso",
            "Cape Lion",
            "Capybara",
            "Caracal",
            "Caribou",
            "Carolina Dog",
            "Carolina Parakeet",
            "Carp",
            "Carpenter Ant",
            "Cashmere Goat",
            "Cassowary",
            "Cat",
            "Catahoula Leopard",
            "Catalan Sheepdog",
            "Caterpillar",
            "Catfish",
            "Cava Tzu",
            "Cavador",
            "Cavalier King Charles Spaniel",
            "Cavapoo",
            "Centipede",
            "Cesky Fousek",
            "Cesky Terrier",
            "Chameleon",
            "Chamois",
            "Chartreux",
            "Cheagle",
            "Cheetah",
            "Chesapeake Bay Retriever",
            "Chickadee",
            "Chicken",
            "Chihuahua",
            "Chimaera",
            "Chimpanzee",
            "Chinchilla",
            "Chinese Alligator",
            "Chinese Crested Dog",
            "Chinese Geese",
            "Chinese Paddlefish",
            "Chinese Shar-Pei",
            "Chinook",
            "Chinstrap Penguin",
            "Chipmunk",
            "Chipoo",
            "Chiweenie",
            "Chorkie",
            "Chow Chow",
            "Chow Shepherd",
            "Christmas Island Red Crab",
            "Cicada",
            "Cichlid",
            "Clouded Leopard",
            "Clownfish",
            "Clumber Spaniel",
            "Coati",
            "Cockalier",
            "Cockapoo",
            "Cockatoo",
            "Cockroach",
            "Codfish",
            "Coelacanth",
            "Collared Peccary",
            "Collie",
            "Colossal Squid",
            "Common Buzzard",
            "Common Frog",
            "Common House Spider",
            "Common Loon",
            "Common Raven",
            "Common Toad",
            "Cookiecutter Shark",
            "Cooper’s Hawk",
            "Coral",
            "Corgidor",
            "Corgipoo",
            "Corkie",
            "Corman Shepherd",
            "Corn Rex Cat (Cornish Rex)",
            "Coton de Tulear",
            "Cotton-top Tamarin",
            "Cougar",
            "Cow",
            "Coyote",
            "Crab",
            "Crab Spider",
            "Crab-Eating Macaque",
            "Crabeater Seal",
            "Crane",
            "Crappie Fish",
            "Crested Penguin",
            "Crocodile",
            "Cross Fox",
            "Cross River Gorilla",
            "Cuckoo",
            "Curly Coated Retriever",
            "Cuscus",
            "Cuttlefish",
            "Czechoslovakian Wolfdog",
            "Dachsador",
            "Dachshund",
            "Dalmadoodle",
            "Dalmador",
            "Dalmatian",
            "Dapple Dachshund",
            "Darwin’s fox",
            "Darwin’s Frog",
            "Deer",
            "Deer Head Chihuahua",
            "Desert Locust",
            "Desert Rain Frog",
            "Desert Tortoise",
            "Deutsche Bracke",
            "Devon Rex",
            "Dhole",
            "Dik-Dik",
            "Dingo",
            "Discus",
            "Doberman Pinscher",
            "Dodo",
            "Dog",
            "Dogo Argentino",
            "Dogue De Bordeaux",
            "Dolphin",
            "Donkey",
            "Dorgi",
            "Dorkie",
            "Dormouse",
            "Double Doodle",
            "Douc",
            "Doxiepoo",
            "Doxle",
            "Draco volans",
            "Dragonfish",
            "Dragonfly",
            "Drever",
            "Drum Fish",
            "Duck",
            "Dugong",
            "Dung Beetle",
            "Dunker",
            "Dusky Dolphin",
            "Dwarf Crocodile",
            "Eagle",
            "Earthworm",
            "Earwig",
            "Eastern Bluebird",
            "Eastern Fence Lizard",
            "Eastern Gorilla",
            "Eastern Lowland Gorilla",
            "Eastern Phoebe",
            "Echidna",
            "Edible Frog",
            "Eel",
            "Egyptian Mau",
            "Electric Eel",
            "Elephant",
            "Elephant Seal",
            "Elephant Shrew",
            "Elk",
            "Emperor Penguin",
            "Emperor Tamarin",
            "Emu",
            "English Cocker Spaniel",
            "English Cream Golden Retriever",
            "English Foxhound",
            "English Pointer",
            "English Setter",
            "English Shepherd",
            "English Springer Spaniel",
            "Entlebucher Mountain Dog",
            "Epagneul Pont Audemer",
            "Ermine",
            "Eskimo Dog",
            "Eskipoo",
            "Estrela Mountain Dog",
            "Falcon",
            "Fallow deer",
            "False Killer Whale",
            "False Widow Spider",
            "Fangtooth",
            "Feist",
            "Fennec Fox",
            "Ferret",
            "Ferruginous Hawk",
            "Fiddler Crab",
            "Field Spaniel",
            "Fila Brasileiro",
            "Fin Whale",
            "Finnish Spitz",
            "Fire salamander",
            "Fire-Bellied Toad",
            "Fish",
            "Fisher Cat",
            "Fishing Cat",
            "Flamingo",
            "Flat-Coated Retriever",
            "Florida Gar",
            "Florida Panther",
            "Flounder",
            "Flowerhorn Fish",
            "Fluke Fish (summer flounder)",
            "Fly",
            "Flying Fish",
            "Flying Lemur",
            "Flying Squirrel",
            "Formosan Mountain Dog",
            "Fossa",
            "Fox",
            "Fox Terrier",
            "French Bulldog",
            "Frenchton",
            "Frengle",
            "Frigatebird",
            "Frilled Lizard",
            "Frilled Shark",
            "Frog",
            "Frogfish",
            "Fruit Bat",
            "Fur Seal",
            "Galapagos Penguin",
            "Galapagos Tortoise",
            "Gar",
            "Garden Eel",
            "Gecko",
            "Genet",
            "Gentoo Penguin",
            "Geoffroys Tamarin",
            "Gerberian Shepsky",
            "Gerbil",
            "German Pinscher",
            "German Shepherd Guide",
            "German Sheppit",
            "German Sheprador",
            "German Shorthaired Pointer",
            "German Spitz",
            "Gharial",
            "Ghost Crab",
            "Giant African Land Snail",
            "Giant Armadillo",
            "Giant Clam",
            "Giant Panda Bear",
            "Giant Salamander",
            "Giant Schnauzer",
            "Giant Schnoodle",
            "Gibbon",
            "Gila Monster",
            "Giraffe",
            "Glass Frog",
            "Glass Lizard",
            "Glechon",
            "Glen Of Imaal Terrier",
            "Glow Worm",
            "Goat",
            "Goberian",
            "Goblin Shark",
            "Goldador",
            "Golden Dox",
            "Golden Lion Tamarin",
            "Golden Masked Owl",
            "Golden Newfie",
            "Golden Oriole",
            "Golden Pyrenees",
            "Golden Retriever",
            "Golden Saint",
            "Golden Shepherd",
            "Golden-Crowned Flying Fox",
            "Goldendoodle",
            "Goliath Frog",
            "Goose",
            "Gopher",
            "Gopher Tortoise",
            "Gordon Setter",
            "Gorilla",
            "Gouldian Finch",
            "Grasshopper",
            "Grasshopper Mouse",
            "Gray Fox",
            "Gray Tree Frog",
            "Great Blue Heron",
            "Great Crested Flycatcher",
            "Great Dane",
            "Great Danoodle",
            "Great Pyrenees",
            "Great White Shark",
            "Greater Swiss Mountain Dog",
            "Green Anole",
            "Green Bee-Eater",
            "Green Frog",
            "Green Tree Frog",
            "Greenland Dog",
            "Greenland Shark",
            "Grey Mouse Lemur",
            "Grey Reef Shark",
            "Grey Seal",
            "Greyhound",
            "Griffonshire",
            "Grizzly Bear",
            "Groenendael",
            "Groundhog (Woodchuck)",
            "Grouse",
            "Guinea Fowl",
            "Guinea Pig",
            "Guppy",
            "Haddock",
            "Hagfish",
            "Hammerhead Shark",
            "Hamster",
            "Harbor Seal",
            "Hardhead Catfish",
            "Hare",
            "Harp Seal",
            "Harpy Eagle",
            "Harrier",
            "Havanese",
            "Havapoo",
            "Havashire",
            "Hawaiian Crow",
            "Hawaiian Monk Seal",
            "Hedgehog",
            "Hercules Beetle",
            "Hermit Crab",
            "Heron",
            "Herring",
            "Highland Cattle",
            "Himalayan",
            "Hippopotamus",
            "Hoary Bat",
            "Hokkaido",
            "Honduran White Bat",
            "Honey Badger",
            "Honey Bee",
            "Hooded Seal",
            "Hoopoe",
            "Horgi",
            "Horn Shark",
            "Hornbill",
            "Horned Frog",
            "Horned Lizard",
            "Horse",
            "Horsefly",
            "Horseshoe Crab",
            "House Finch",
            "House wren",
            "Howler Monkey",
            "Human",
            "Humboldt Penguin",
            "Humboldt Squid",
            "Hummingbird",
            "Humpback Whale",
            "Huntaway",
            "Huntsman Spider",
            "Huskador",
            "Huskita",
            "Husky Jack",
            "Huskydoodle",
            "Hyena",
            "Ibex",
            "Ibis",
            "Ibizan Hound",
            "Icelandic Sheepdog",
            "Iguana",
            "Immortal Jellyfish",
            "Impala",
            "Imperial Moth",
            "Indian Elephant",
            "Indian Giant Squirrel",
            "Indian Palm Squirrel",
            "Indian Rhinoceros",
            "Indian Star Tortoise",
            "Indochinese Tiger",
            "Indri",
            "Insects",
            "Irish Doodle",
            "Irish Setter",
            "Irish Terrier",
            "Irish Water Spaniel",
            "Irish WolfHound",
            "Italian Greyhound",
            "Ivory-billed woodpecker",
            "Jacana",
            "Jack Russell",
            "Jackabee",
            "Jackal",
            "Jackdaw",
            "Jackson’s Chameleon",
            "Jaguar",
            "Jaguarundi Cat",
            "Japanese Beetle",
            "Japanese Chin",
            "Japanese Macaque",
            "Japanese Spitz",
            "Japanese Squirrel",
            "Japanese Terrier",
            "Javan Rhinoceros",
            "Javanese",
            "Jellyfish",
            "Jerboa",
            "Joro Spider",
            "Kai Ken",
            "Kakapo",
            "Kangal",
            "Kangaroo",
            "Kangaroo Rat",
            "Keel-Billed Toucan",
            "Keeshond",
            "Kerry Blue Terrier",
            "Key Deer",
            "Kiko Goat",
            "Killer Whale",
            "Kinder Goat",
            "King Cobra",
            "King Crab",
            "King Penguin",
            "King Shepherd",
            "Kingfisher",
            "Kinkajou",
            "Kishu",
            "Kiwi",
            "Koala",
            "Kodkod",
            "Koi Fish",
            "Komodo Dragon",
            "Kooikerhondje",
            "Kookaburra",
            "Koolie",
            "Krill",
            "Kudu",
            "Kuvasz",
            "Labahoula",
            "Labmaraner",
            "Labradane",
            "Labradoodle",
            "Labrador Retriever",
            "Labraheeler",
            "Ladybug",
            "Lakeland Terrier",
            "LaMancha Goat",
            "Lamprey",
            "Lancashire Heeler",
            "Lapponian Herder",
            "Leaf-Tailed Gecko",
            "Lemming",
            "Lemur",
            "Leonberger",
            "Leopard",
            "Leopard Cat",
            "Leopard Frog",
            "Leopard Gecko",
            "Leopard Seal",
            "Leopard Tortoise",
            "Lhasa Apso",
            "Lhasapoo",
            "Liger",
            "Lion",
            "Lion’s Mane Jellyfish",
            "Lionfish",
            "Little Brown Bat",
            "Little Penguin",
            "Lizard",
            "Llama",
            "Loach",
            "Lobster",
            "Locust",
            "Long-Eared Owl",
            "Long-Haired Rottweiler",
            "Longnose Gar",
            "Lorikeet",
            "Lowchen",
            "Lungfish",
            "Lynx",
            "Lyrebird",
            "Macaroni Penguin",
            "Macaw",
            "Mackenzie Wolf",
            "Magellanic Penguin",
            "Magpie",
            "Maine Coon",
            "Malayan Civet",
            "Malayan Tiger",
            "Mallard",
            "Malteagle",
            "Maltese",
            "Maltese Shih Tzu",
            "Maltipoo",
            "Man of War Jellyfish",
            "Manatee",
            "Manchester Terrier",
            "Mandrill",
            "Maned Wolf",
            "Manta Ray",
            "Mantella Frog",
            "Marabou Stork",
            "Marble Fox",
            "Marine Iguana",
            "Marine Toad",
            "Markhor",
            "Marmot",
            "Marsh Frog",
            "Masked Palm Civet",
            "Mastador",
            "Mastiff",
            "Mayfly",
            "Meagle",
            "Mealybug",
            "Meerkat",
            "Megalodon",
            "Megamouth Shark",
            "Mekong Giant Catfish",
            "Mexican Alligator Lizard",
            "Mexican Free-Tailed Bat",
            "Mexican Mole Lizard",
            "Miki",
            "Milkfish",
            "Millipede",
            "Mini Labradoodle",
            "Miniature Bull Terrier",
            "Miniature Husky",
            "Miniature Pinscher",
            "Mink",
            "Minke Whale",
            "Mississippi Kite",
            "Mole",
            "Mole Cricket",
            "Molly",
            "Monarch Butterfly",
            "Mongoose",
            "Mongrel",
            "Monitor Lizard",
            "Monkey",
            "Monkfish",
            "Monte Iberia Eleuth",
            "Moorhen",
            "Moose",
            "Moray Eel",
            "Morkie",
            "Moscow Watchdog",
            "Moth",
            "Mountain Bluebird",
            "Mountain Cur",
            "Mountain Feist",
            "Mountain Gorilla",
            "Mountain Lion",
            "Mourning Dove",
            "Mouse",
            "Mudi",
            "Mudpuppy",
            "Mule",
            "Muntjac",
            "Muskox",
            "Muskrat",
            "Myna Bird",
            "Naked Mole Rat",
            "Narwhal",
            "Neanderthal",
            "Neapolitan Mastiff",
            "Nebelung",
            "Newfoundland",
            "Newfypoo",
            "Newt",
            "Nigerian Goat",
            "Nightingale",
            "Nile Crocodile",
            "No See Ums",
            "Norfolk Terrier",
            "North American Black Bear",
            "Northern Alligator Lizard",
            "Northern Cardinal",
            "Northern Fur Seal",
            "Northern Inuit Dog",
            "Norwegian Buhund",
            "Norwegian Elkhound",
            "Norwegian Forest",
            "Norwegian Lundehund",
            "Norwich Terrier",
            "Nova Scotia Duck Tolling Retriever",
            "Nubian Goat",
            "Numbat",
            "Nurse Shark",
            "Ocelot",
            "Octopus",
            "Okapi",
            "Old English Sheepdog",
            "Olm",
            "Opossum",
            "Orang-utan",
            "Orb Weaver",
            "Oscar Fish",
            "Ostrich",
            "Otter",
            "Otterhound",
            "Oyster",
            "Oyster Toadfish",
            "Paddlefish",
            "Pademelon",
            "Painted Turtle",
            "Pangolin",
            "Panther",
            "Papillon",
            "Parakeet",
            "Parrot",
            "Parrotlet",
            "Parson Russell Terrier",
            "Patas Monkey",
            "Patterdale Terrier",
            "Peacock",
            "Peacock Butterfly",
            "Peacock Spider",
            "Peagle",
            "Peekapoo",
            "Pekingese",
            "Pelican",
            "Pembroke Welsh Corgi",
            "Penguin",
            "Pere Davids Deer",
            "Peregrine Falcon",
            "Perro De Presa Canario",
            "Persian",
            "Peruvian Inca Orchid",
            "Petit Basset Griffon Vendéen",
            "Petite Goldendoodle",
            "Pheasant",
            "Pied Tamarin",
            "Pig",
            "Pigeon",
            "Pika",
            "Pike Fish",
            "Pileated Woodpecker",
            "Pine Marten",
            "Pink Fairy Armadillo",
            "Piranha",
            "Pitador",
            "Pitsky",
            "Platypus",
            "Pocket Beagle",
            "Pocket Pitbull",
            "Pointer",
            "Poison Dart Frog",
            "Polar Bear",
            "Polish Lowland Sheepdog",
            "Polyphemus moth",
            "Pomapoo",
            "Pomchi",
            "Pomeagle",
            "Pomeranian",
            "Pomsky",
            "Pond Skater",
            "Poochon",
            "Poodle",
            "Poogle",
            "Pool Frog",
            "Porbeagle Shark",
            "Porcupine",
            "Porpoise",
            "Possum",
            "Potoroo",
            "Prairie Dog",
            "Prairie Rattlesnake",
            "Prawn",
            "Proboscis Monkey",
            "Pronghorn",
            "Pudelpointer",
            "Pufferfish",
            "Puffin",
            "Pug",
            "Pugapoo",
            "Puggle",
            "Pugshire",
            "Puma",
            "Pumi",
            "Purple Emperor Butterfly",
            "Purple Finch",
            "Puss Moth",
            "Pygmy Hippopotamus",
            "Pygmy Marmoset (Finger Monkey)",
            "Pygora Goat",
            "Pyrador",
            "Pyredoodle",
            "Quagga",
            "Quail",
            "Quetzal",
            "Quokka",
            "Quoll",
            "Rabbit",
            "Raccoon",
            "Raccoon Dog",
            "Radiated Tortoise",
            "Ragamuffin",
            "Ragdoll",
            "Raggle",
            "Rat",
            "Rat Terrier",
            "Rattlesnake",
            "Red Finch",
            "Red Fox",
            "Red Knee Tarantula",
            "Red Panda",
            "Red Squirrel",
            "Red Wolf",
            "Red-handed Tamarin",
            "Red-winged blackbird",
            "Redback Spider",
            "Redbone Coonhound",
            "Reef Shark",
            "Reindeer",
            "Rhinoceros",
            "River Turtle",
            "Robin",
            "Rock Crab",
            "Rock Hyrax",
            "Rockfish",
            "Rockhopper Penguin",
            "Rodents",
            "Roe Deer",
            "Rose-breasted Grosbeak",
            "Roseate Spoonbill",
            "Rottsky",
            "Rottweiler",
            "Royal Penguin",
            "Ruby-Throated Hummingbird",
            "Russell Terrier",
            "Russian Bear Dog",
            "Russian Blue",
            "Russian Tortoise",
            "Saanen Goat",
            "Saarloos Wolfdog",
            "Saber-Toothed Tiger",
            "Sable",
            "Sable Black German Shepherd",
            "Saiga",
            "Saint Berdoodle",
            "Saint Bernard",
            "Saint Shepherd",
            "Salamander",
            "Salmon",
            "Salmon Shark",
            "Saluki",
            "Samoyed",
            "Sand Lizard",
            "Sand Tiger Shark",
            "Saola",
            "Sardines",
            "Satanic leaf-tailed gecko",
            "Savanna Goat",
            "Sawfish",
            "Scarlet Macaw",
            "Schipperke",
            "Schneagle",
            "Schnoodle",
            "Scimitar-horned Oryx",
            "Scorpion",
            "Scorpion Fish",
            "Scottish Terrier",
            "Scrotum Frog",
            "Sea Anemone",
            "Sea Dragon",
            "Sea Eagle",
            "Sea Lion",
            "Sea Otter",
            "Sea Slug",
            "Sea Squirt",
            "Sea Turtle",
            "Sea Urchin",
            "Seagull",
            "Seahorse",
            "Seal",
            "Sealyham Terrier",
            "Sei Whale",
            "Senegal Parrot",
            "Serval",
            "Shark",
            "Sharp-Tailed Snake",
            "Sheep",
            "Sheepadoodle",
            "Shepadoodle",
            "Shepkita",
            "Shepweiler",
            "Shiba Inu",
            "Shih Poo",
            "Shih Tzu",
            "Shoebill Stork",
            "Shollie",
            "Shrimp",
            "Siamese",
            "Siamese Fighting Fish",
            "Siberian",
            "Siberian Husky",
            "Siberian Ibex",
            "Siberian Retriever",
            "Siberian Tiger",
            "Siberpoo",
            "Silkie Chicken",
            "Silky Terrier",
            "Silver Dollar",
            "Silver Labrador",
            "Sixgill shark",
            "Skate Fish",
            "Skink Lizard",
            "Skipjack Tuna",
            "Skunk",
            "Skye Terrier",
            "Sleeper Shark",
            "Sloth",
            "Slow Worm",
            "Slug",
            "Smooth Fox Terrier",
            "Snail",
            "Snake",
            "Snapping Turtle",
            "Snorkie",
            "Snow Leopard",
            "Snowshoe",
            "Snowshoe Hare",
            "Snowy Owl",
            "Somali",
            "Song Sparrow",
            "South China Tiger",
            "Spadefoot Toad",
            "Spanador",
            "Spanish Goat",
            "Spanish Mastiff",
            "Sparrow",
            "Spectacled Bear",
            "Sperm Whale",
            "Spider Monkey",
            "Spider Wasp",
            "Spinner Shark",
            "Spinone Italiano",
            "Spiny Dogfish",
            "Spiny Hill Turtle",
            "Spixs Macaw",
            "Sponge",
            "Spotted Gar",
            "Spotted Lanternfly",
            "Springador",
            "Springbok",
            "Springerdoodle",
            "Squid",
            "Squirrel",
            "Squirrel Monkey",
            "Squirrelfish",
            "Sri Lankan Elephant",
            "Stabyhoun",
            "Staffordshire Bull Terrier",
            "Stag Beetle",
            "Standard Schnauzer",
            "Star-nosed mole",
            "Starfish",
            "Steller’s Sea Cow",
            "Stick Insect",
            "Stingray",
            "Stoat",
            "Stone Crab",
            "Stork",
            "Striped Hyena",
            "Striped Rocket Frog",
            "Sturgeon",
            "Sucker Fish",
            "Sugar Glider",
            "Sulcata Tortoise",
            "Sumatran Elephant",
            "Sumatran Orang-utan",
            "Sumatran Rhinoceros",
            "Sumatran Tiger",
            "Sun Bear",
            "Swai Fish",
            "Swan",
            "Swedish Vallhund",
            "Syrian Hamster",
            "Taco Terrier",
            "Takin",
            "Tamaskan",
            "Tang",
            "Tapanuli Orang-utan",
            "Tapir",
            "Tarantula Hawk",
            "Tarpon",
            "Tarsier",
            "Tasmanian Devil",
            "Tasmanian Tiger",
            "Tawny Owl",
            "Teddy Bear Hamster",
            "Teddy Roosevelt Terrier",
            "Tenterfield Terrier",
            "Termite",
            "Terrier",
            "Tetra",
            "Texas Heeler",
            "Thai Ridgeback",
            "Thorny Devil",
            "Thrush",
            "Tibetan Fox",
            "Tibetan Mastiff",
            "Tibetan Spaniel",
            "Tibetan Terrier",
            "Tiffany",
            "Tiger",
            "Tiger Beetle",
            "Tiger Moth",
            "Tiger Salamander",
            "Tiger Shark",
            "Toadfish",
            "Torkie",
            "Tortoise",
            "Toucan",
            "Toy Fox Terrier",
            "Toy Poodle",
            "Transylvanian Hound",
            "Tree Frog",
            "Tree Kangaroo",
            "Tree swallow",
            "Treeing Tennessee Brindle",
            "Treeing Walker Coonhound",
            "Tropicbird",
            "Tsetse Fly",
            "Tuatara",
            "Tuna",
            "Turkey",
            "Turkish Angora",
            "Turtles",
            "Uakari",
            "Uguisu",
            "Umbrellabird",
            "Utonagan",
            "Vampire Bat",
            "Vampire Squid",
            "Vaquita",
            "Vervet Monkey",
            "Vinegaroon",
            "Virgin Islands Dwarf Gecko",
            "Vizsla",
            "Volpino Italiano",
            "Vulture",
            "Walking Catfish",
            "Wallaby",
            "Walleye Fish",
            "Walrus",
            "Wandering Albatross",
            "Warthog",
            "Wasp",
            "Water Buffalo",
            "Water Dragon",
            "Water Vole",
            "Weasel",
            "Weimaraner",
            "Weimardoodle",
            "Welsh Corgi",
            "Welsh Terrier",
            "West Highland Terrier",
            "Western Gorilla",
            "Western Lowland Gorilla",
            "Westiepoo",
            "Whale Shark",
            "Wheaten Terrier",
            "Whippet",
            "White Butterfly",
            "White Rhinoceros",
            "White Tiger",
            "White-Faced Capuchin",
            "White-tail deer",
            "White-Tailed Eagle",
            "Whoodle",
            "Whooping Crane",
            "Wild Boar",
            "Wildebeest",
            "Willow Warbler",
            "Wire Fox Terrier",
            "Wirehaired Pointing Griffon",
            "Wolf",
            "Wolf Eel",
            "Wolf Spider",
            "Wolffish",
            "Wolverine",
            "Wombat",
            "Wood Bison",
            "Wood Frog",
            "Wood Turtle",
            "Woodlouse",
            "Woodlouse Spider",
            "Woodpecker",
            "Woolly Mammoth",
            "Woolly Monkey",
            "Wrasse",
            "Wyoming Toad",
            "X-Ray Tetra",
            "Xerus",
            "Xoloitzcuintli",
            "Yak",
            "Yakutian Laika",
            "Yellow Sac Spider",
            "Yellow-Eyed Penguin",
            "Yellowfin Tuna",
            "Yeti Crab",
            "Yoranian",
            "Yorkie Bichon",
            "Yorkie Poo",
            "Yorkshire Terrier",
            "Zebra",
            "Zebra Finch",
            "Zebra Mussels",
            "Zebra Pleco",
            "Zebra Shark",
            "Zebu",
            "Zonkey",
            "Zorse",
            "Zuchon"
    };
}