package agh.ics.oop;

import java.util.Map;

public enum MapDirection {
    NORTH{
        @Override
        public String toString(){
          return "Północ";
        };
        @Override
        public MapDirection next(){
            return EAST;
        }
        @Override
        public MapDirection previous(){
            return WEST;
        }
        @Override
        public Vector2d toUnitVector(){
            return new Vector2d(0,1);
        }
    },
    SOUTH{
        @Override
        public String toString(){
            return "Południe";
        };
        @Override
        public MapDirection next(){
            return WEST;
        }
        @Override
        public MapDirection previous(){
            return EAST;
        }
        @Override
        public Vector2d toUnitVector(){
            return new Vector2d(0,-1);
        }
    },
    WEST{
        @Override
        public String toString(){
            return "Zachód";
        };
        @Override
        public MapDirection next(){
            return NORTH;
        }
        @Override
        public MapDirection previous(){
            return SOUTH;
        }
        @Override
        public Vector2d toUnitVector(){
            return new Vector2d(-1,0);
        }
    },
    EAST{
        @Override
        public String toString(){
            return "Wschód";
        };
        @Override
        public MapDirection next(){
            return SOUTH;
        }
        @Override
        public MapDirection previous(){
            return NORTH;
        }
        @Override
        public Vector2d toUnitVector(){
            return new Vector2d(1,0);
        }
    };

    public abstract Vector2d toUnitVector();
    public abstract MapDirection previous();
    public abstract MapDirection next();
}
