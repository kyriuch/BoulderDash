package nodomain.boulderdash.gameobjects;

public abstract class GameObject implements IGameObject
{
    private Clickable clickable;

    public void OnClick() {
        if(clickable != null) {
            clickable.OnClick();
        }
    }

    public void setClickListener(Clickable clickable) {
        this.clickable = clickable;
    }
}
