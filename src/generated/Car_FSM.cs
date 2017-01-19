public abstract class Car_FSM{
	public Car_FSMState currentState = Car_FSMState.Locked;
	private void SetState(Car_FSMState newState){
	ExecuteExitFunction(currentState);
	currentState = newState;
	ExecuteEntryFunction(newState);
	}
	public enum Car_FSMState{
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
	case Car_FSMState.Locked :
		switch(receivedEvent){
		case Key.Open :
		Unlock();
		SetState(Car_FSMState.Unlocked);
		break;
		case Key.Force :
		Notify();
		Lock();
		SetState(Car_FSMState.Alarming);
		break;
		}
	break;
	case Car_FSMState.Alarming :
		switch(receivedEvent){
		case Key.Reset :
		Lock();
		SetState(Car_FSMState.Locked);
		break;
		}
	break;
	case Car_FSMState.Unlocked :
		switch(receivedEvent){
		case Key.Close :
		Lock();
		SetState(Car_FSMState.Locked);
		break;
		case Key.Reset :
		Lock();
		SetState(Car_FSMState.Locked);
		break;
		}
	break;
	}
	}
	private void ExecuteEntryFunction(Car_FSMState state){
		switch(state){
		case Car_FSMState.Alarming: AlarmOn(); break;
		}
	}
	private void ExecuteExitFunction(Car_FSMState state){
		switch(state){
		case Car_FSMState.Alarming: AlarmOff(); break;
		}
	}
	protected abstract void AlarmOff();
	protected abstract void AlarmOn();
	protected abstract void Lock();
	protected abstract void Notify();
	protected abstract void Unlock();
}
