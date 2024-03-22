const CustomButton = ({ buttonName, action }: any) => {
    return (
        <button type="button" onClick={action}>{buttonName}</button>
    );
}

export default CustomButton;