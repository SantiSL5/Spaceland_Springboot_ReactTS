import Footer from "./footer";
import Header from "./header";


const Layout = (props: any) => {
    return (
        <div style={{
            display: "flex",
            flexFlow: "column",
            backgroundColor: "#F1F1F1"
        }}>
            <Header title="Spacecraft"></Header>
            {props.children}
            <Footer title="A"></Footer>
        </div>
    );
}

export default Layout;