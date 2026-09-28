const $ = id => document.getElementById(id);

function showMessage(text, error=false){
    const box=$('message'); box.textContent=text; box.className='message'+(error?' error':''); box.style.display='block';
    setTimeout(()=>box.style.display='none',3500);
}

async function api(url, options={}){
    const res=await fetch(url,{headers:{'Content-Type':'application/json',...(options.headers||{})},...options});
    const data=await res.json().catch(()=>({}));
    if(!res.ok) throw new Error(data.error || data.message || 'Request failed');
    return data;
}

async function loadDashboard(){
    const d=await api('/api/dashboard');
    $('owners').textContent=d.owners; $('pets').textContent=d.pets; $('types').textContent=d.vaccineTypes; $('due').textContent=d.dueNext7Days;
}

async function loadOwners(){
    const owners=await api('/api/owners');
    $('petOwner').innerHTML='<option value="">Select owner</option>'+owners.map(o=>`<option value="${o.id}">${escapeHtml(o.name)}</option>`).join('');
}

async function loadPets(){
    const pets=await api('/api/pets');
    const html='<option value="">Select pet</option>'+pets.map(p=>`<option value="${p.id}">${escapeHtml(p.name)} (${escapeHtml(p.species)})</option>`).join('');
    $('vaccinationPet').innerHTML=html; $('historyPet').innerHTML=html;
}

async function loadTypes(){
    const types=await api('/api/vaccine-types');
    $('vaccinationType').innerHTML='<option value="">Select vaccine type</option>'+types.map(v=>`<option value="${v.id}">${escapeHtml(v.name)} — ${v.intervalDays} days</option>`).join('');
}

async function loadDue(){
    const rows=await api('/api/vaccinations/due-next-7-days');
    $('dueTable').innerHTML=rows.length?table(
        ['Pet','Owner','Vaccine','Date Given','Next Due'],
        rows.map(r=>[r.pet.name,r.pet.owner.name,r.vaccineType.name,r.dateGiven,r.nextDueDate])
    ):'<p>No vaccinations are due in the next 7 days.</p>';
    await loadDashboard();
}

async function loadHistory(){
    const petId = $('historyPet').value;

    if (!petId) {
        $('historyTable').innerHTML = '<p>Select a pet first.</p>';
        return;
    }

    try {
        const rows = await api('/api/vaccinations/pet/' + petId);

        if (!rows || rows.length === 0) {
            $('historyTable').innerHTML = '<p>No vaccination records found for this pet.</p>';
            return;
        }

        // The API returns every recorded vaccination from the pet's first
        // vaccination through today. Every returned record is a completed event.
        $('historyTable').innerHTML = table(
            ['Vaccine', 'Vaccination Date', 'Next Due', 'Interval', 'Status'],
            rows.map(r => [
                r.vaccineName || '-',
                r.dateGiven || '-',
                r.nextDueDate || '-',
                r.intervalDays ? r.intervalDays + ' days' : '-',
                r.status || 'Completed'
            ])
        );
    } catch (err) {
        $('historyTable').innerHTML =
            '<p class="error-text">Unable to load vaccination history: ' +
            escapeHtml(err.message) + '</p>';
    }
}

function table(headers,rows){
    return `<table class="table"><thead><tr>${headers.map(h=>`<th>${h}</th>`).join('')}</tr></thead><tbody>${rows.map(row=>`<tr>${row.map(c=>`<td>${escapeHtml(String(c??''))}</td>`).join('')}</tr>`).join('')}</tbody></table>`;
}
function escapeHtml(value){return value.replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));}

$('ownerForm').addEventListener('submit',async e=>{
    e.preventDefault();
    try{
        await api('/api/owners',{method:'POST',body:JSON.stringify({name:$('ownerName').value,email:$('ownerEmail').value,phone:$('ownerPhone').value})});
        e.target.reset(); await loadOwners(); await loadDashboard(); showMessage('Owner registered successfully.');
    }catch(err){showMessage(err.message,true);}
});

$('petForm').addEventListener('submit',async e=>{
    e.preventDefault();
    try{
        await api('/api/pets?ownerId='+encodeURIComponent($('petOwner').value),{method:'POST',body:JSON.stringify({
            name:$('petName').value,species:$('petSpecies').value,breed:$('petBreed').value,dateOfBirth:$('petDob').value
        })});
        e.target.reset(); await loadPets(); await loadDashboard(); showMessage('Pet registered successfully.');
    }catch(err){showMessage(err.message,true);}
});

$('vaccinationForm').addEventListener('submit',async e=>{
    e.preventDefault();
    try{
        const r=await api('/api/vaccinations',{method:'POST',body:JSON.stringify({
            petId:Number($('vaccinationPet').value),vaccineTypeId:Number($('vaccinationType').value),dateGiven:$('dateGiven').value
        })});
        e.target.reset(); await loadDashboard(); await loadDue(); showMessage('Vaccination logged. Next due date: '+r.nextDueDate);
    }catch(err){showMessage(err.message,true);}
});

(async function init(){
    $('today').textContent=new Date().toLocaleDateString(undefined,{year:'numeric',month:'long',day:'numeric'});
    try{
        await Promise.all([loadDashboard(),loadOwners(),loadPets(),loadTypes(),loadDue()]);
    }catch(err){showMessage(err.message,true);}
})();
