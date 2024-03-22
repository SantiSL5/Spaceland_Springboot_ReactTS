import { useState } from "react";
import Spinner from "../../components/spinner/spinner.component";
import { useUsers } from "../../hooks/useUsers";

const Home = () => {
    // const { stationsWithSlots, getStationsWithSlots } = useStations();
    // const { lastRent, rentBike, returnBike, getRentInfo } = useRents();
    // const { user } = useUsers();
    // const { incidence, getIncidenceById, createIncidenceUser } = useIncidences();

    // if (!stationsWithSlots) getStationsWithSlots();
    // const [selectedSlot, setSelectedSlot]: any = useState();

    // const getModalInfo = (slot: any) => {
    //     getIncidenceById(slot.id)
    //     if (user) {
    //         getRentInfo()
    //     }
    //     setSelectedSlot(slot);
    // }

    return (
        <div className="container-fluid row">
            <div className="">
                Home
            </div>
        </div >
    );
}

export default Home;