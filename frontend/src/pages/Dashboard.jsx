export default function Dashboard() {
  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">Dashboard</h2>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-purple-100 rounded-xl p-6 text-center shadow">
          <p className="text-4xl font-bold text-purple-700">📦</p>
          <p className="text-lg font-semibold text-gray-700 mt-2">Pedidos</p>
          <p className="text-gray-500 text-sm">Gestiona los pedidos</p>
        </div>
        <div className="bg-blue-100 rounded-xl p-6 text-center shadow">
          <p className="text-4xl font-bold text-blue-700">🏭</p>
          <p className="text-lg font-semibold text-gray-700 mt-2">Inventario</p>
          <p className="text-gray-500 text-sm">Controla el stock</p>
        </div>
        <div className="bg-green-100 rounded-xl p-6 text-center shadow">
          <p className="text-4xl font-bold text-green-700">🚚</p>
          <p className="text-lg font-semibold text-gray-700 mt-2">Envios</p>
          <p className="text-gray-500 text-sm">Seguimiento de despachos</p>
        </div>
      </div>
    </div>
  )
}