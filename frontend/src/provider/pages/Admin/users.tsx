import { useState } from "react";
import ListUsers from "../../components/admin/crudUsers/list.component";
import CreateUser from "../../components/admin/crudUsers/create.component";
import MenuAdmin from "../../components/admin/menuAdmin/menuAdmin.component";
import Spinner from "../../components/spinner/spinner.component";
import { useUsers } from "../../hooks/useUsers";


const AdminUsers = () => {
    const { users, getAllUsers, createUser, deleteUser, deleteManyUsers, updateUser } = useUsers();
    const [op, setOp]: any = useState("create");
    const [updateData, setupdateData]: any = useState();

    if (!users) getAllUsers();

    const changeForm = (data: any, op: string) => {
        if (op == "create") {
            setOp(op);
            setupdateData(null)
        } else {
            setOp(op);
            setupdateData(data)
        }
    }

    return (
        <div className="adminView">
            <MenuAdmin />
            <CreateUser createUser={createUser} operation={op} updateData={updateData} updateUser={updateUser} changeForm={changeForm} />
            {users ? <ListUsers list={users} deleteUser={deleteUser} deleteManyUsers={deleteManyUsers} changeForm={changeForm} updateUser={updateUser}></ListUsers> : <Spinner />}
        </div>
    );
}

export default AdminUsers;