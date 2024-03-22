export const saveUser = (title: any) =>
    (dispatch: any) => {
        console.log(title);

        dispatch({
            type: 'SAVE_USER',
            payload: title
        })
    }
