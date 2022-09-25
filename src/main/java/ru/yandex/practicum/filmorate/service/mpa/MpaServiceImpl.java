package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.MpaDaoImpl;

import java.util.List;
import java.util.Optional;
@Service
public class MpaServiceImpl implements MpaService{
    private final MpaDaoImpl mpaDao;

    @Autowired
    public MpaServiceImpl(MpaDaoImpl mpaDao) {
        this.mpaDao = mpaDao;
    }

    @Override
    public Optional<Mpa> getMpaById(int id) {
        return mpaDao.getMpaById(id);
    }

    @Override
    public List<Mpa> getAllMpa() {
        return mpaDao.getAllMpa();
    }

}
