const fetchUserReducer = (state = null, action: any) => {
    switch (action.type) {
        case 'SAVE_USER':
            console.log('reducer');
            console.log(action.payload);
            
            return action.payload;
        default:
            return state;
    }
}

const rootReducer = (state: any, action: any) => {
    return {
        user: fetchUserReducer(state, action)
    }
}

export default rootReducer;
