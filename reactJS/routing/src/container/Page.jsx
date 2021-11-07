import React from "react";
import { useParams } from 'react-router-dom';

function Page() {
  const { id } = useParams();
  return <>
    <h1>Ini Pages <span>{id}</span></h1>
  </>
}

export default Page;