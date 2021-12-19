package agh.ics.oop.simulation;

import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.objects.IMapElement;

public interface IPositionChangeObserver {
    void positionChanged(IMapElement element, Vector2d newPosition);
}
