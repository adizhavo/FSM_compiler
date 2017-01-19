public abstract class Car{
	public CarState currentState = CarState.Locked;
	private void SetState(CarState newState){
	ExecuteExitFunction(currentState);
	currentState = newState;
	ExecuteEntryFunction(newState);
	}
	public enum CarState{
	Alarming,
	Unlocked,
	Locked,
	}
	public enum Key {
	Open,
	Force,
	Close,
	Reset,
	}
	public void SendEvent(Key receivedEvent){
	switch(currentState){
	case CarState.Locked :
		switch(receivedEvent){
		case Key.Open :
		Unlock();
		SetState(CarState.Unlocked);
		break;
		case Key.Force :
		Notify();
		Lock();
		SetState(CarState.Alarming);
		break;
		}
	break;
	case CarState.Alarming :
		switch(receivedEvent){
		case Key.Reset :
		SetState(CarState.Locked);
		break;
		}
	break;
	case CarState.Unlocked :
		switch(receivedEvent){
		case Key.Close :
		Lock();
		SetState(CarState.Locked);
		break;
		case Key.Reset :
		Lock();
		SetState(CarState.Locked);
		break;
		}
	break;
	}
	}
	private void ExecuteEntryFunction(CarState state){
		switch(state){
		case CarState.Alarming: AlarmOn(); break;
		}
	}
	private void ExecuteExitFunction(CarState state){
		switch(state){
		case CarState.Alarming: AlarmOff(); break;
		}
	}
	protected abstract void AlarmOff();
	protected abstract void AlarmOn();
	protected abstract void Lock();
	protected abstract void Notify();
	protected abstract void Unlock();
}
