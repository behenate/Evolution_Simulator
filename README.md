# Evolution Simulator

This application was created for Object Oriented Programming course on AGH UST.  The entire project has been written in Java.

The main goal is to create an animal, which can survive and reproduce based on 32 numbers ranging from 0-7.

### General rules

Each animal is placed on a virtual 2D map represented by a grid. In order to survive the animal has to have energy level greater than 0. If the energy level is above 50% of the initial energy and the animal meets another animal, they reproduce, the resulting child has its genome generated based on genomes and energy levels of the parents. Each move and reproduction costs the animal energy, in order to replenish its energy the animal has to eat food placed on the map.

### Genome

Each animal has its genome. It consists of 32 numbers from 0 to 7. Each number represents a movement direction on the map 0 is move foreward, 1 is rotate 45 deg, 2 is rotate 90 deg, etc. Each day based a move is randomly chosen for the animal based on its genome. For example, an animal with genome consisting of 32 zeroes would move forwards until it dies.

### Maps

There are two types of maps for the simulation

- Rolled map - When an animal runs into the edge of the map and tries to walk into the wall it gets teleported onto the other side.
- Walled map - When an animal runs into the edge of the map and tries to walk into the wall it gets stopped until it changes direction.

### Configuration

The simulation can be configured before launch. Below is a screenshot of possible options.

<img src="desc_imgs\config.jpg" alt="config" style="zoom:60%;" />

### Functionality

Besides running the simulation the app also can:

- Graph the relevant simulation statistics in real time (graphs shown on the left and the right of the screen)
- Select a specific animal by clicking on it, the statistics of the chosen animal will be displayed in the screen below the map
- Show animals with dominant genome - shows all animals that are genetic clones of each other and share the same genome.
- Save simulation to file - Saves the relevant simulation statistics shown on graphs to a .csv file

### Screenshot

Below is a screenshot of the application running 



![scr1](C:\Users\behen\Documents\Studia\Obiektowe\Kod\Obiektowe_Projekt_1\desc_imgs\scr1.jpg)



### Instructions

The application was created based on requirements posted on this page:

https://github.com/apohllo/obiektowe-lab/tree/master/proj1

### A cute doggo meme for your enjoyment:



![img](https://i.imgur.com/yc0V1JP.jpeg)



