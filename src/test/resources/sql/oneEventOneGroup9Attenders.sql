insert into events(attendee_id, created_at, organizer_id, planned_start, ready, id)
values ('c129864c-7bca-486a-a98d-02e98a0a44d6',
        '2024-10-30T20:26:14.132478600',
        'ba359df4-bf04-4772-8f7d-ad3bb6481503',
        null,
        false,
        1);

insert into groups(id, event_id, group_type, ready)
values (1,
        1,
        'ARCHER',
        false);

insert into attender(id, group_id, actual_profession, required_profession, nickname)
values (1,1,'Silver Ranger','ARCHER', 'Asbiel');

insert into attender(id, group_id, actual_profession, required_profession, nickname)
values (2,1,'Hawkeye','ARCHER', 'PolskiŁucznikPL');

insert into attender(id, group_id, actual_profession, required_profession, nickname)
values (3,1,'Phantom Ranger','ARCHER', 'Szczała');

insert into attender(id, group_id, actual_profession, required_profession, nickname)
values (4,1,'Sword Singer','BARD', 'Wiadomka');

insert into attender(id, group_id, actual_profession, required_profession, nickname)
values (5,1,'Blade Dancer','BARD', 'Marcyska');

insert into attender(id, group_id, actual_profession, required_profession, nickname)
values (6,1,'Warcryer','BUFFER', 'PhantomShaman');

insert into attender(id, group_id, actual_profession, required_profession, nickname)
values (7,1,'Bishop','HEALER', 'MaSaPl');

insert into attender(id, group_id, actual_profession, required_profession, nickname)
values (8,1,'Shilien Elder','RECHARGER', 'Sesonka');

insert into attender(id, group_id, actual_profession, required_profession, nickname)
values (9,1,'Elven Elder','RECHARGER', 'Przempol');