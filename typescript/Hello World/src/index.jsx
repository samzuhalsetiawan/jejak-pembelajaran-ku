import React from "react";
import ReactDOM from "react-dom";
import { v4 as uuidV4 } from "uuid";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<h1>Hello From React</h1>);
root.render(<h1>Hello From UUID {uuidV4()}</h1>);